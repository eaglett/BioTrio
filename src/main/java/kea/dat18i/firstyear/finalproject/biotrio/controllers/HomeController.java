package kea.dat18i.firstyear.finalproject.biotrio.controllers;

import kea.dat18i.firstyear.finalproject.biotrio.QRcodeGenerator.QRsender;
import kea.dat18i.firstyear.finalproject.biotrio.QRcodeGenerator.QRwriter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    @GetMapping(value = {"/", "/home"})
    public String homePage() {

        QRwriter qRwriter = new QRwriter();

//        qRwriter.writeQR("Supp Yenny, fancy stuff, right?");

        QRsender qRsender = new QRsender();

        String[] recipients = { "madalina.pascariu0305@gmail.com", "saidalisic@gmail.com" };
//        qRsender.sendEmail( recipients, "QRdir\\QRCODE5.png";);






        return "home";
    }





}
