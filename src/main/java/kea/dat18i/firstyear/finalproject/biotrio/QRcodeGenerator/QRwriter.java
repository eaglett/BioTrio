package kea.dat18i.firstyear.finalproject.biotrio.QRcodeGenerator;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRwriter {


    // Takes a String parameter to store text inside a QR encoding
    public void writeQR(String QRmsg) {

        createDirForQR("QRdir");

        try {
            String filePath = "QRdir\\QRCODE5.png";
            String charset = "UTF-8"; // or "ISO-8859-1"
            Map < EncodeHintType, ErrorCorrectionLevel > hintMap = new HashMap < EncodeHintType, ErrorCorrectionLevel > ();
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            BitMatrix matrix = new MultiFormatWriter().encode(
                    new String(QRmsg.getBytes(charset), charset),
                    BarcodeFormat.QR_CODE, 200, 200, hintMap);
            MatrixToImageWriter.writeToFile(matrix, filePath.substring(filePath
                    .lastIndexOf('.') + 1), new File(filePath));
            System.out.println("QR Code image created successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("QR code creation malfunctioned");
        }

    }

    // Creates a new directory to store our QR code files into
    private void createDirForQR(String dirName) {
        File dir = new File(dirName);
        if(!dir.exists()) {
            dir.mkdir();
            System.out.println("Directory " + dirName + " has been created!");
        }


    }

}
