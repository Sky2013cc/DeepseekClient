package com.deepseek.client.module.impl;

import com.deepseek.client.module.*;
import java.util.*;

public class Friend extends Module {
    private final List<String> friends = new ArrayList<>();
    public Friend() { super("Friend", "好友系统", Category.MISC, 0); }
    public void add(String name) { if (!friends.contains(name)) friends.add(name); }
    public void remove(String name) { friends.remove(name); }
    public boolean isFriend(String name) { return friends.contains(name); }
    public List<String> getFriends() { return friends; }
}
