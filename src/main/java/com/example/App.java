package com.example;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class App {
    public static final int DEFAULT_PORT = 8081;

    public static String getGreeting() {
        return "Hello, World from Docker in WSL via Jenkins!";
    }

    public static void main(String[] args) throws IOException {
        int port = DEFAULT_PORT;
        String envPort = System.getenv("PORT");
        if (envPort != null && !envPort.isEmpty()) {
            try {
                port = Integer.parseInt(envPort);
            } catch (NumberFormatException ignored) {}
        }

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new RootHandler());
        server.createContext("/health", new HealthHandler());
        server.setExecutor(null);

        System.out.println("==================================================");
        System.out.println(getGreeting());
        System.out.println("Web server started successfully at http://0.0.0.0:" + port);
        System.out.println("==================================================");

        server.start();
    }

    static class RootHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String javaVersion = System.getProperty("java.version");
            String osName = System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ")";

            String htmlResponse = "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    <title>Java App - Docker & Jenkins in WSL</title>\n" +
                    "    <style>\n" +
                    "        body {\n" +
                    "            font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif;\n" +
                    "            background: linear-gradient(135deg, #1f2937, #111827);\n" +
                    "            color: #f3f4f6;\n" +
                    "            display: flex;\n" +
                    "            justify-content: center;\n" +
                    "            align-items: center;\n" +
                    "            min-height: 100vh;\n" +
                    "            margin: 0;\n" +
                    "        }\n" +
                    "        .card {\n" +
                    "            background: #1e293b;\n" +
                    "            border: 1px solid #334155;\n" +
                    "            border-radius: 16px;\n" +
                    "            padding: 36px 40px;\n" +
                    "            max-width: 520px;\n" +
                    "            box-shadow: 0 20px 25px -5px rgba(0, 0, 0, 0.5);\n" +
                    "            text-align: center;\n" +
                    "        }\n" +
                    "        .badge {\n" +
                    "            display: inline-block;\n" +
                    "            background: #059669;\n" +
                    "            color: #ffffff;\n" +
                    "            padding: 4px 14px;\n" +
                    "            border-radius: 9999px;\n" +
                    "            font-size: 13px;\n" +
                    "            font-weight: 600;\n" +
                    "            margin-bottom: 16px;\n" +
                    "        }\n" +
                    "        h1 {\n" +
                    "            margin: 0 0 12px 0;\n" +
                    "            font-size: 26px;\n" +
                    "            color: #38bdf8;\n" +
                    "        }\n" +
                    "        p.subtitle {\n" +
                    "            color: #94a3b8;\n" +
                    "            font-size: 15px;\n" +
                    "            margin-bottom: 24px;\n" +
                    "        }\n" +
                    "        .details {\n" +
                    "            background: #0f172a;\n" +
                    "            border-radius: 10px;\n" +
                    "            padding: 16px;\n" +
                    "            text-align: left;\n" +
                    "            font-size: 14px;\n" +
                    "            line-height: 1.8;\n" +
                    "            margin-bottom: 20px;\n" +
                    "        }\n" +
                    "        .details strong {\n" +
                    "            color: #cbd5e1;\n" +
                    "        }\n" +
                    "        .footer {\n" +
                    "            font-size: 12px;\n" +
                    "            color: #64748b;\n" +
                    "        }\n" +
                    "    </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <div class=\"card\">\n" +
                    "        <div class=\"badge\">● Active &amp; Running in Docker</div>\n" +
                    "        <h1>" + getGreeting() + "</h1>\n" +
                    "        <p class=\"subtitle\">Continuous Integration &amp; Deployment with Jenkins &amp; GitHub</p>\n" +
                    "        <div class=\"details\">\n" +
                    "            <div><strong>Server Time:</strong> " + timestamp + "</div>\n" +
                    "            <div><strong>Java Version:</strong> " + javaVersion + "</div>\n" +
                    "            <div><strong>Operating System:</strong> " + osName + "</div>\n" +
                    "            <div><strong>Port:</strong> 8081</div>\n" +
                    "        </div>\n" +
                    "        <div class=\"footer\">Deployed automatically by Jenkins in WSL</div>\n" +
                    "    </div>\n" +
                    "</body>\n" +
                    "</html>";

            byte[] bytes = htmlResponse.getBytes("UTF-8");
            exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            exchange.sendResponseHeaders(200, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        }
    }

    static class HealthHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String json = "{\"status\":\"UP\"}";
            byte[] bytes = json.getBytes("UTF-8");
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        }
    }
}
