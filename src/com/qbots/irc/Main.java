package com.qbots.irc;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;

import java.io.IOException;

/**
 * Created by Tyler on 1/6/14.
 */
public class Main {

    static QBot bot;

    public static void main(String[] args) throws IrcException, IOException {
        bot = new QBot("DahQBot",0);
        bot.connect("irc.rizon.net",6667);
        bot.sendMessage("nickserv", "");
        bot.joinChannel("#excobot");
    }
}
