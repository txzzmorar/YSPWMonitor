package yspwmonitor;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Tarun
 */
public class YSPWMonitor {

    public static String url;
    
    
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scan4 = new Scanner(new File("webhook.txt"));
        List<String> hookList = new ArrayList<String>();
        while (scan4.hasNextLine()) {
            hookList.add(scan4.nextLine());
        }
        
        Scanner scan = new Scanner(System.in);
        System.out.println("What website do you want to monitor e.g https://yeezysupply.com");
        url = scan.nextLine();
        System.out.println("Password Page Monitor Made by @txzzmorar");
        System.out.println("Starting Password Page Monitor");
        System.out.println("v3.0");
        System.out.println("---");
        System.out.println("What monitor delay do you want?");
        String inputdelay = scan.nextLine();
        int delay = Integer.parseInt(inputdelay);
        boolean hasPassword = checkForPassword();
        while(!hasPassword){
            System.out.println(getTime()+"Password page not found");
            hasPassword = checkForPassword();
            Thread.sleep(delay);
        }
        JSONObject author =  new JSONObject();
        author.put("text","Made by @Txzzmorar");
        JSONObject embed =  new JSONObject();
        embed.put("title",url);
        embed.put("url", url);
        embed.put("description",getTime()+" PASSWORD PAGE LIVE!");
        embed.put("color","16754488");
        embed.put("footer", author);
        
        
        JSONArray arr = new JSONArray();
        arr.put(embed);
        
        JSONObject embed2 =  new JSONObject();
        embed2.put("title",url);
        embed2.put("url", url);
        embed2.put("description",getTime()+" PASSWORD PAGE DOWN!");
        embed2.put("color","16711680");
        embed2.put("footer", author);
        
        
        JSONArray arr2 = new JSONArray();
        arr2.put(embed2);
        JSONObject obj2 = new JSONObject();
        obj2.put("embeds", arr2);
        obj2.put("content","@everyone");
        
        JSONObject obj = new JSONObject();
        obj.put("embeds", arr);
        obj.put("content","@everyone");
        System.out.println(getTime()+"Password page live!");
        String DiscordWebHook = hookList.get(0);
           Document webhook = Jsoup.connect(DiscordWebHook)
                .requestBody(obj.toString())
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.62 Safari/537.36")
                .post();
        boolean didPassGoDown = false;
        while(!didPassGoDown){
            boolean StillLive = checkForPassword();
            if (StillLive){
                System.out.println(getTime()+"Password Still Up");
                Thread.sleep(delay);
            }
            else if (!StillLive){
            
        
                System.out.println("Password Down! - Live!");
                Document webhook2 = Jsoup.connect(DiscordWebHook)
                        .requestBody(obj2.toString())
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.62 Safari/537.36")
                        .post();
                didPassGoDown = true;
            }
            
            
        }
    }
    
    public static boolean checkForPassword() throws IOException{
        Scanner scan = new Scanner(new File("proxies.txt"));
        List<String> proxyList = new ArrayList<String>();
        while (scan.hasNextLine()) {
            proxyList.add(scan.nextLine());
        }
        int lenOfProxies = proxyList.size();
        Random rn = new Random();
        int proxyUse = 0 + rn.nextInt(lenOfProxies-0 + 0);
        String proxy = proxyList.get(proxyUse);
        List<String> proxySplit = Arrays.asList(proxy.split(":"));
        
        String proxyIP = proxySplit.get(0);
        String port = proxySplit.get(1);
        String authUser = proxySplit.get(2);
        String authPassword =  proxySplit.get(3);
        Authenticator.setDefault(
           new Authenticator() {
              @Override
              public PasswordAuthentication getPasswordAuthentication() {
                 return new PasswordAuthentication(
                       authUser, authPassword.toCharArray());
                }
             }
          );
        System.setProperty("http.proxyHost", proxyIP);
        System.setProperty("http.proxyPort", port);
        System.setProperty("http.proxyUser", authUser);
        System.setProperty("http.proxyPassword", authPassword);
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
        String timeStamp = new SimpleDateFormat("HH:mm:ss:SSS").format(new Date());
        String newTime = "["+timeStamp+"]";
        return newTime;
    }
}
    

