package com.finance.communication.common;

import com.alibaba.druid.pool.DruidDataSource;

@SuppressWarnings("all")
public class DecryptDruidSource extends DruidDataSource{
    
@Override
public void setUsername(String username) {
try {
username = ConfigTools.decrypt(username);
} catch (Exception e) {
e.printStackTrace();
}
super.setUsername(username);
}
}