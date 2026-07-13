package com.adridevelop.journalytic_backend.models.service;

import java.io.InputStream;
import java.net.InetAddress;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CountryResponse;

import jakarta.annotation.PostConstruct;

@Service
public class GeoIpService {

    private DatabaseReader databaseReader;

    @PostConstruct
    public void init(){
        try{
            InputStream database = new ClassPathResource("GeoLite2-Country.mmdb").getInputStream();
            this.databaseReader = new DatabaseReader.Builder(database).build();
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }

    public String obtenerCodigoPorPais(String ipAddress){
        if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1") || ipAddress.startsWith("192.168.")){
            return "LOCAL";
        }

        try{
            InetAddress ip = InetAddress.getByName(ipAddress);
            CountryResponse country = databaseReader.country(ip);
            return country.getCountry().getIsoCode();
        }catch(Exception e){
            return "NOT FOUND";
        }
    }

}
