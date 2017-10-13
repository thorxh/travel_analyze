package com.bonc.usdp.util;

import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;

import javax.swing.text.SimpleAttributeSet;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * created on 2017/9/25
 *
 * @author liyanjun@bonc.com.cn
 */
public class FileUtil {

    public static void appendList(String path, List<String> dataList) {
        File file = new File(path);
        try(Sink sink = Okio.appendingSink(file);
            BufferedSink buffer = Okio.buffer(sink)) {
            for (Object data : dataList) {
                buffer.writeUtf8(data.toString()).writeUtf8("\n");
            }
            buffer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeList(String path, List<?> dataList) {
        File file = new File(path);
        try(Sink sink = Okio.sink(file);
            BufferedSink buffer = Okio.buffer(sink)) {
            for (Object data : dataList) {
                buffer.writeUtf8(data.toString()).writeUtf8("\n");
            }
            buffer.flush();
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
            buffer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> read(String path) {
        List<String> list = new LinkedList<>();
        try(BufferedSource bufferedSource = Okio.buffer(Okio.source(new File(path)))) {
            while (!bufferedSource.exhausted()) {
                list.add(bufferedSource.readUtf8Line());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void deleteFileOrDir(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        if (!file.isDirectory()) {
            file.delete();
        } else {
            File[] subFiles = file.listFiles();
            if (subFiles != null) {
                Arrays.stream(subFiles).forEachOrdered(File::delete);
            }
        }
    }

}
