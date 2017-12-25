/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yspwmonitor;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author Tarun
 */
public class YSPWMonitor {

    /**
     * @param args the command line arguments
     */
    static String url = "https://yeezysupply.com";
    
    
    public static void main(String[] args) throws IOException, InterruptedException {
        // TODO code application logic here
        System.setProperty("http.proxyHost", "localhost");
        System.setProperty("http.proxyPort", "80");
        System.out.println("Password Page Monitor Made by @txzzmorar");
        System.out.println("Starting YeezySupply Password Page Monitor");
        System.out.println("v3.0");
        System.out.println("---");
        System.out.println("What monitor delay do you want?");
        Scanner scan = new Scanner(System.in);
        String inputdelay = scan.nextLine();
        int delay = Integer.parseInt(inputdelay);
        boolean hasPassword = checkForPassword();
        while(!hasPassword){
            System.out.println(getTime()+"Password page not found");
            hasPassword = checkForPassword();
            Thread.sleep(delay);
        }
        System.out.println(getTime()+"Password page live!");
        String DiscordWebHook = "https://discordapp.com/api/webhooks/xxx/xxx";
           Document webhook = Jsoup.connect(DiscordWebHook)
                .data("content",getTime()+url+" PASSWORD PAGE UP- TEST")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.62 Safari/537.36")
                .post();
           //System.out.println(webhook.body());
        boolean didPassGoDown = false;
        while(!didPassGoDown){
            //System.out.println("Pass still up");
            boolean StillLive = checkForPassword();
            if (StillLive){
                System.out.println(getTime()+"Password Still Up");
                Thread.sleep(delay);
            }
            else if (!StillLive){
            
        
                System.out.println("Password Down - YeezySupply Live!");
                Document webhook2 = Jsoup.connect(DiscordWebHook)
                        .data("content",getTime()+url+" LIVE - TEST")
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.62 Safari/537.36")
                        .post();
                didPassGoDown = true;
            }
            
            
        }
    }
    
    public static boolean checkForPassword() throws IOException{
        boolean Password = false;
        try{
            Connection.Response doc = Jsoup.connect(url)
                    .execute();
            doc.body();
            if (doc.url().toString().contains("/password")){
                Password = true;
            }
        }
        catch(IOException ex){
            Password = false;
        }
        return Password;
    }
    
    public static String getTime(){
        String timeStamp = new SimpleDateFormat("HH.mm.ss").format(new Date());
        String newTime = "["+timeStamp+"]";
        return newTime;
    }
}
    

