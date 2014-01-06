package com.qbots.irc;

import com.qbots.irc.util.QCommands;
import org.jibble.pircbot.PircBot;
import org.jibble.pircbot.User;

import java.io.FileInputStream;
import java.util.*;

/**
 * Created by Tyler on 1/6/14.
 */
public class QBot extends PircBot {
    protected final QCommands commands;
    public Map<String, String> ignoreList = new LinkedHashMap();
    public final String API_KEY = "6490a24c-bccc-4a05-8a0e-b3752aa35da2";
    public boolean greet = true;
    private final int id;

    public QBot(String name, int id) {
        this.setName(name);
        this.commands = new QCommands(this);
        this.id = id;
    }

    public void onMessage(final String channel, final String sender, final String login, final String hostname, final String message) {
        if (isIgnored(sender)) {
            return;
        }
        char heading = message.charAt(0);
        if (heading == '%') {
            String[] sp = message.split(" ");
            sp[0] = sp[0].toLowerCase().substring(1);
            commands.processCommand(heading, sp, channel, getUser(channel, sender));
        }
        if (message.contains("The moderators of this room are")) {
            System.out.println("True");
        }
    }

    public void onKick(String channel, String kickerNick, String kickerLogin, String kickerHostname, String recipientNick, String reason) {
        if (isConnected()) {
            sendRawLine("JOIN " + channel);
        }
    }

    public User getUser(final String channel, final String nick) {
        User[] users = getUsers(channel);
        for (User user : users) {
            if (user.getNick().equalsIgnoreCase(nick)) {
                return user;
            }
        }
        return null;
    }

    public boolean isIgnored(final String nick) {
        for (String s : ignoreList.keySet()) {
            if (s.equalsIgnoreCase(nick)) {
                return true;
            }
        }
        return false;
    }

    public void onConnect() {
        Properties properties = new Properties();
        try {
            FileInputStream in = new FileInputStream(getServer() + "_commands.properties");
            properties.load(in);
            in.close();
        } catch (Exception e) {
            log("Error loading commands for " + getServer() + " :" + e.getMessage());
        }
        for (final Map.Entry<Object, Object> entry : properties.entrySet()) {
            if (!commands.getCommandMap().containsKey(entry.getKey())) {
                commands.getCommandMap().put((String) entry.getKey(), (String) entry.getValue());
            }
        }

        Properties props = new Properties();
        try {
            FileInputStream in = new FileInputStream(getServer() + "_ignore.txt");
            props.load(in);
            in.close();
        } catch (Exception e) {
            log("Error loading ignore file for " + getServer() + ": " + e.getMessage());
        }
        for (Object o : props.keySet()) {
            if (!ignoreList.containsKey(o)) {
                ignoreList.put((String) o, (String) props.get(o));
            }
        }
    }

    public void onJoin(String channel, String sender, String login, String hostname) {
        if (!sender.equalsIgnoreCase(getNick()) && greet) {
            sendMessage(channel, "Hullo " + sender);
        }
    }

    public void onPart(String channel, String sender, String login, String hostname) {
        if (!sender.equalsIgnoreCase(getNick()) && greet) {
            sendMessage(channel, "RIP " + sender);
        }
    }

    public void log(String line) {
        //Main.log("[" + TIME_FORMAT.format(Calendar.getInstance().getTime()) + "] " + line, id);
        System.out.println(line);
    }

    public void setGreet(boolean greet) {
        this.greet = greet;
    }

    public boolean getGreet() {
        return this.greet;
    }

}
