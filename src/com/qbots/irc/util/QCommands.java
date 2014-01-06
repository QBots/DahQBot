package com.qbots.irc.util;

import com.eclipsesource.json.JsonObject;
import com.qbots.irc.QBot;
import com.qbots.irc.util.league.LeagueApi;
import com.qbots.irc.util.league.Summoner;
import org.jibble.pircbot.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Tyler on 1/6/14.
 */
public class QCommands {

    public QCommands(QBot bot) {
        this.bot = bot;
    }

    protected final QBot bot;
    protected final Map<String, String> commands = new LinkedHashMap();
    protected final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("hh:mm:ss a z");
    PriceGrab prices = PriceGrab.getInstance();

    public Map<String, String> getCommandMap() {
        return commands;
    }

    public void processCommand(final char heading, final String[] sub, final String channel, final User sender) {
        try {
            if (commands.containsKey(sub[0].toLowerCase())) {
                String[] msg = commands.get(sub[0]).split(":", 2);
                if (msg[0].equalsIgnoreCase("raw")) {
                    bot.sendRawLine(msg[1]);
                    bot.log("Sending RAW line: " + msg[1]);
                    return;
                }
                bot.sendMessage(channel, commands.get(sub[0]));
                return;
            }

            if(sub[0].equalsIgnoreCase("add") && canAdd(sender)) {
                if(sub.length <= 1) {
                    bot.sendMessage(channel,"Failed args. Needed: add command result");
                    return;
                } else {
                    if(commands.containsKey(sub[1])) {
                        bot.sendMessage(channel,"Command already exists... :/");
                    } else {
                        StringBuilder sb = new StringBuilder("");
                        for (int i = 0; i < (sub.length - 2); i++) {
                            sb.append(sub[i + 2]).append(" ");
                        }
                        commands.put(sub[1].toLowerCase(), sb.toString());
                        bot.sendMessage(channel,"Command "+ sub[1] +" -> " + sb.toString());
                    }
                }
            } else if(sub[0].equalsIgnoreCase("remove") && canAdd(sender)) {
                if(sub.length <= 1 || !commands.containsKey(sub[1])) {
                    bot.sendMessage(channel,"Failed... :l");
                    return;
                } else {
                    if(commands.containsKey(sub[1])) {
                        bot.sendMessage(channel,"Command is now gone. ;)");
                        commands.remove(sub[1]);
                    }
                }
            } else if(sub[0].equalsIgnoreCase("price")) {
                StringBuilder sb = new StringBuilder("");
                for(int i = 1; i < sub.length; i++) {
                    sb.append(sub[i]);
                    if(i < sub.length) sb.append(" ");
                }
                System.out.println(sb.toString());
                bot.sendMessage(channel,"Price of "+sb.toString()+"is "+prices.getPrice(sb.toString(),2));
            } else if(sub[0].equalsIgnoreCase("sit")) {
                //System.out.println(sender.getNick());
                if(sender.getNick().contains("rawr") || sender.getNick().contains("dog") || sender.getNick().equalsIgnoreCase("rawr")) {
                    bot.sendMessage(channel,"Yes, my master");
                } else {
                    bot.sendMessage(channel,"Dafuq you just say to me?");
                }
            } else if(sub[0].equalsIgnoreCase("Stats")) {
                checkLeague(sub,channel);
            } else if(sub[0].equalsIgnoreCase("StatsId")) {
                checkLeagueId(sub,channel);
            } else if(sub[0].equalsIgnoreCase("kill")) {
                bot.sendMessage(channel,"I cannot be killed.");
            }
        } catch(Exception e) {
            e.printStackTrace();
            //System.out.println("Error: "+e.get());
        }
    }

    private boolean canAdd(User user) {
        return user.getNick().contains("QBots");
    }

    private void checkLeague(String[] sub,final String channel) {
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < (sub.length - 2); i++) {
            sb.append(sub[i + 2]).append(" ");
        }
        sb.deleteCharAt(sb.lastIndexOf(" "));
        Summoner summoner = null;
        try {
            summoner = new LeagueApi().getSummoner("na",sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(summoner != null){
            String[] split = summoner.toString().split("\n");
            for(int i = 0; i < split.length; i++) {
                bot.sendMessage(channel,split[i]);
            }
            //bot.sendMessage(channel,summoner.toString());
        }
        //bot.sendMessage(channel,"Response message: "+player.get("message"));
    }

    private void checkLeagueId(String[] sub,final String channel) {
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < (sub.length - 2); i++) {
            sb.append(sub[i + 2]).append(" ");
        }
        sb.deleteCharAt(sb.lastIndexOf(" "));
        Summoner summoner = null;
        try {
            summoner = new LeagueApi().getOtherInfo("n/a",Integer.parseInt(sb.toString()),"na");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(summoner != null){
            String[] split = summoner.toString().split("\n");
            for(int i = 0; i < split.length; i++) {
                bot.sendMessage(channel,split[i]);
            }
            //bot.sendMessage(channel,summoner.toString());
        }
        //bot.sendMessage(channel,"Response message: "+player.get("message"));
    }

}
