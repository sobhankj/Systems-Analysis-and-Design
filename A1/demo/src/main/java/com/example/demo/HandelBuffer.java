//package com.example.demo;
//
//import org.springframework.jms.core.JmsTemplate;
//import org.springframework.stereotype.Service;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Service
//public class HandelBuffer {
//
//    private final JmsTemplate jmsTemplate;
//    private Map<String, Integer> portfolio = new HashMap<>();
//
//    public HandelBuffer(JmsTemplate jmsTemplate) {
//        this.jmsTemplate = jmsTemplate;
//    }
//
//    public String processMessage(String message) {
//        String[] parts = message.trim().split("\\s+");
//        if (parts.length == 0) {
//            return "1 Invalid command";
//        }
//
//        String command = parts[0].toUpperCase();
//        switch (command) {
//            case "ADD":
//                if (parts.length < 2) return "1 Invalid ADD command format";
//                return processAdd(parts[1]);
//
//            case "BUY":
//                if (parts.length < 3) return "1 Invalid BUY command format";
//                try {
//                    int amount = Integer.parseInt(parts[2]);
//                    return processBuy(parts[1], amount);
//                } catch (NumberFormatException e) {
//                    return "1 Invalid amount format in BUY command";
//                }
//
//            case "SELL":
//                if (parts.length < 3) return "1 Invalid SELL command format";
//                try {
//                    int amount = Integer.parseInt(parts[2]);
//                    return processSell(parts[1], amount);
//                } catch (NumberFormatException e) {
//                    return "1 Invalid amount format in SELL command";
//                }
//
//            case "PORTFOLIO":
//                return getPortfolio();
//
//            default:
//                return "1 Unknown command";
//        }
//    }
//
//    private String processAdd(String security) {
//        if (portfolio.containsKey(security)) {
//            return "1 Security already exists";
//        }
//        portfolio.put(security, 0);
//        return "0 Success";
//    }
//
//    private String processBuy(String security, int amount) {
//        if (!portfolio.containsKey(security)) {
//            return "1 Unknown security";
//        }
//        portfolio.put(security, portfolio.get(security) + amount);
//        return "0 Trade successful";
//    }
//
//    private String processSell(String security, int amount) {
//        if (!portfolio.containsKey(security)) {
//            return "1 Unknown security";
//        }
//        int currentAmount = portfolio.get(security);
//        if (currentAmount < amount) {
//            return "2 Not enough positions";
//        }
//        portfolio.put(security, currentAmount - amount);
//        return "0 Trade successful";
//    }
//
//    private String getPortfolio() {
//        if (portfolio.isEmpty()) {
//            return "0 Portfolio is empty";
//        }
//        StringBuilder sb = new StringBuilder("0 ");
//        portfolio.forEach((sec, amt) -> sb.append(sec).append(": ").append(amt).append(" | "));
//        return sb.toString().trim();
//    }
//
//    public void sendResponse(String response) {
//        jmsTemplate.convertAndSend("OUTQ", response);
//        System.out.println("Sent response: " + response);
//    }
//}


package com.example.demo;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class HandelBuffer {

    private final JmsTemplate jmsTemplate;
    private Map<String, Integer> portfolio = new HashMap<>();

    public HandelBuffer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public String handleMessage(String input) {
        String[] elements = input.trim().split("\\s+");
        if (elements.length == 0) {
            return "1 Invalid command";
        }

        String action = elements[0].toUpperCase();
        switch (action) {
            case "ADD":
                return (elements.length < 2) ? "1 Invalid ADD format" : registerSecurity(elements[1]);

            case "BUY":
                return (elements.length < 3) ? "1 Invalid BUY format" : executeBuy(elements[1], elements[2]);

            case "SELL":
                return (elements.length < 3) ? "1 Invalid SELL format" : executeSell(elements[1], elements[2]);

            case "PORTFOLIO":
                return showPortfolio();

            default:
                return "1 Unrecognized command";
        }
    }

    private String registerSecurity(String stock) {
        if (portfolio.containsKey(stock)) {
            return "1 Already Exists";
        }
        portfolio.put(stock, 0);
        return "0 Success";
    }

    private String executeBuy(String stock, String quantity) {
        if (!portfolio.containsKey(stock)) {
            return "1 Unknown security";
        }
        try {
            int amount = Integer.parseInt(quantity);
            portfolio.put(stock, portfolio.get(stock) + amount);
            return "0 Trade successful";
        } catch (NumberFormatException e) {
            return "1 Invalid quantity format";
        }
    }

    private String executeSell(String stock, String quantity) {
        if (!portfolio.containsKey(stock)) {
            return "1 Unknown security";
        }
        try {
            int amount = Integer.parseInt(quantity);
            int available = portfolio.get(stock);
            if (available < amount) {
                return "2 Not enough positions";
            }
            portfolio.put(stock, available - amount);
            return "0 Trade successful";
        } catch (NumberFormatException e) {
            return "1 Invalid quantity format";
        }
    }

    private String showPortfolio() {
        if (portfolio.isEmpty()) {
            return "0 Portfolio is empty";
        }
        StringBuilder summary = new StringBuilder("0 ");
        portfolio.forEach((stock, amount) -> summary.append(stock).append(": ").append(amount).append(" | "));
        return summary.toString().trim();
    }

    public void respondToQueue(String message) {
        jmsTemplate.convertAndSend("OUTQ", message);
        System.out.println("Sent response: " + message);
    }
}