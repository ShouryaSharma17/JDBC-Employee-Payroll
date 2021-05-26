package com.database;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.IntStream;

public class NIOFileAPITest {
    private static String HOME = System.getProperty("user.home");
    private static String PLAY_WITH_NIO = "TempPlayGround";

    //UC2 Demonstrate File Operations
    @Test
    public void givenPathWhenCheckedThenConfirm() throws IOException {
        Path homePath = Paths.get(HOME);
        Assertions.assertTrue(Files.exists(homePath));

        Path playPath = Paths.get(HOME + "/" + PLAY_WITH_NIO);
        if(Files.exists(playPath))
            FileUtils.deleteFiles(playPath.toFile());
        Assertions.assertTrue(Files.notExists(playPath));

        Files.createDirectory(playPath);
        Assertions.assertTrue(Files.exists(playPath));

        IntStream.range(1,10).forEach(count -> {
            Path tempFile = Paths.get(playPath + "/temp" + count);
            Assertions.assertTrue(Files.notExists(tempFile));
            try{
                Files.createFile(tempFile);
            }catch(IOException e){
                Assertions.assertTrue(Files.exists(tempFile));
            }
        });

        Files.list(playPath).filter(Files::isRegularFile).forEach(System.out::println);
        Files.newDirectoryStream(playPath).forEach(System.out::println);
        Files.newDirectoryStream(playPath, path -> path.toFile().isFile() &&
                path.toString().startsWith("temp"))
                .forEach(System.out::println);
    }

    //UC3 Create a Watch service to watch particular directory, files and sub directories
    @Test
    public void givenDirectoryWhenWatched_ListsAllTheActivities() throws IOException {
        Path dir = Paths.get(HOME+ "/"+PLAY_WITH_NIO);
        Files.list(dir).filter(Files::isRegularFile).forEach(System.out::println);
        new JavaWatcherServiceExample(dir).processEvents();
    }
}

