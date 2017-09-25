package com.bonc.usdp.util;

import okio.BufferedSink;
import okio.Okio;
import okio.Sink;

import javax.swing.text.SimpleAttributeSet;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * created on 2017/9/25
 *
 * @author liyanjun@bonc.com.cn
 */
public class FileUtil {

    public static void writeList(String path, List<?> dataList) {
        File file = new File(path);
        try(Sink sink = Okio.sink(file);
            BufferedSink buffer = Okio.buffer(sink)) {
            for (Object data : dataList) {
                buffer.writeUtf8(data.toString()).writeUtf8("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeMap(String path, Map<String, String> dataMap) {
        File file = new File(path);
        try(Sink sink = Okio.sink(file);
            BufferedSink buffer = Okio.buffer(sink)) {
            for (Map.Entry<String, String> data : dataMap.entrySet()) {
                buffer.writeUtf8(data.getKey()).writeUtf8(" : ").writeUtf8(data.getValue()).writeUtf8("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void cleanDir(String path) {
        File file = new File(path);
        if (file.exists() && file.isDirectory()) {
            File[] subFiles = file.listFiles();
            if (subFiles != null) {
                Arrays.stream(subFiles).forEachOrdered(File::delete);
            }
        }
    }

}
