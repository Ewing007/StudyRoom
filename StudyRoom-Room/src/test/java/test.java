import com.ewing.RoomApplication;
import com.ewing.manager.QRCodeGeneratorManager;
import com.google.zxing.WriterException;
import org.junit.jupiter.api.Test;

import java.io.IOException;


/**
 * @Author: Ewing
 * @Date: 2024-11-28-19:35
 * @Description:
 */
public class test {

    @Test
    public void test() throws IOException, WriterException {

        // 生成二维码内容
        String qrContent = "user_id=" +"123456" + "&reservation_id=" + "1234567890";

        // 生成二维码
        String generateQRCodeBase64 = QRCodeGeneratorManager.generateQRCodeBase64(qrContent, 200, 200);

        // 打印二维码
        System.out.println(generateQRCodeBase64);
    }
}
