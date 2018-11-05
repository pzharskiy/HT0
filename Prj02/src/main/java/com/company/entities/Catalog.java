package com.company.entities;

import com.company.entities.Artist;
import com.company.exceptions.AccessException;
import com.company.exceptions.NotExistingDirectoryException;
import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class Catalog {
    private static final Logger loggerForDublicates = Logger.getLogger("fileOfDublicates");
    private static final Logger loggerForDublicatesWithoutChecksum = Logger.getLogger("fileOfDublicatesWithoutChecksum");

    List<Artist> artists = new ArrayList<Artist>();

    public Catalog(String directorylist[]) throws NotExistingDirectoryException {
        for (String directory : directorylist
                ) {
            File folder = new File(directory);
            //если folder существует, его можно прочитать и он не скрытый (для системных папок, например $Recycle)
            if (folder.exists() && folder.canRead() && !folder.isHidden()) {
                File listOfFiles[] = folder.listFiles();
                treeTraversal(directory, listOfFiles); //Рекурсивный обход всего каталога
            } else
                throw new NotExistingDirectoryException("Данной директории не существуют, она является скрытой, или ее невозможно прочитать. Проверьте введенный вами путь: " + directory);
        }
    }

    public void print() {

        if (artists.isEmpty()) {
            System.out.println("List of artists is empty");
        } else {
            for (Artist artist : artists
                    ) {
                artist.print();
            }
        }
    }

    public void printToFile() {

        StringBuilder html = new StringBuilder("<!DOCTYPE html>\n<html>\n<meta charset=\"utf-8\"\n<title></title>\n" +
                "<style>\np{\n padding-left: 20px;\n}\nh4{\n padding-left: 10px;\n}\n</style>\n" +
                "</head>\n<body>\n<div>\n");

        for (Artist artist : artists
                ) {
            html.append(artist.printToFile());
        }
        html.append("</div>\n</body>\n</html>");

        File file = new File("./resources/list of songs.html");
        try (BufferedWriter bw = new BufferedWriter((new FileWriter(file)))) {
            bw.write(html.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void add(File directoryItem) {
        artists.add(new Artist(directoryItem));
    }

    private void treeTraversal(String path, File listOfFiles[]) throws AccessException {
        for (File directoryItem : listOfFiles) {
            if (directoryItem.canRead() && !directoryItem.isHidden()) {
                //Если пункт каталога другой каталог, то вызываем рекурсивную функцию
                if (directoryItem.isDirectory()) {
                    File folder = new File(directoryItem.getPath());
                    File list[] = folder.listFiles();
                    treeTraversal(directoryItem.getPath(), list);
                }
                //Если пункт каталога файл, то проверяем расширение (необходимо mp3)
                if (directoryItem.isFile()) {
                    //Проверка расширения файла
                    if (!isMP3(directoryItem)) {
                        continue;
                    }
                    //Если такой артист уже есть в каталоге, то запрашиваем этого артиста и пробуем добавить альбом
                    if (artists.contains(new Artist(directoryItem))) {
                        getArtist(directoryItem).addAlbum(directoryItem);

                    }
                    //Иначе добавляем нового артиста
                    else {
                        artists.add(new Artist(directoryItem));
                    }

                }
            }
            //else throw new AccessException("Нет доступа");
        }
    }

    public Artist getArtist(File directoryItem) {

        Artist checkingArtist = new Artist(directoryItem);
        //Проверка, существует ли исполнитель переданной песни (файла)
        for (Artist artist : artists
                ) {
            if (artist.getName().equals(checkingArtist.getName())) {
                return artist;
            }
        }
        return null;
    }

    private boolean isMP3(File directoryItem) {
        String extension = "";
        int i = directoryItem.getName().lastIndexOf('.');
        if (i > 0) {
            extension = directoryItem.getName().substring(i + 1);
        }
        if (extension.equals("mp3")) {
            return true;
        } else return false;
    }

    public void findDublicates() {
        if (artists.isEmpty()) {
            System.out.println("List of artists is empty");
        } else {
            StringBuilder dublicates = new StringBuilder();
            for (Artist artist : artists
                    ) {
                dublicates.append(artist.findDublicates());
            }
            loggerForDublicates.info(dublicates.toString());
        }
    }

    public void findDublicatesWithoutCheckSum() {
        if (artists.isEmpty()) {
            System.out.println("List of artists is empty");
        } else {
            //Создаем карту, где ключ - это название песни, а значения - список из песен-дубликатов
            Map<String, List<Song>> map = new HashMap<String, List<Song>>();
            map = createMap();
            printMap(map);
        }
    }

    private Map<String, List<Song>> createMap() {
        Map<String, List<Song>> map = new HashMap<String, List<Song>>();
        List<Song> list = new ArrayList<>();
        //Проходим по всем песням и заносим их в список list
        for (Artist artist : artists
                ) {
            list.addAll(artist.findDublicatesWithoutCheckSum());
        }
        /*Проходимя по всему списку list, если в карте не содержится ключ, совпадающий с названием очередной песни,
         то снова проходим по list и добавляем в subList все песни, совпадающие по названию, исполнитлею и альбому.
         после этого subList заносим в карту с ключом - названием песни
        */
        for (Song song : list
                ) {
            List<Song> subList = new ArrayList<>();
            if (!map.containsKey(song.getTitle())) {
                for (Song songItem : list
                        ) {
                    if (songItem.getTitle().equals(song.getTitle()) && songItem.getAlbum().equals(song.getAlbum()) && songItem.getArtist().equals(song.getArtist())) {

                        subList.add(songItem);
                    }
                }
            } else continue;
            map.put(song.getTitle(), subList);
        }
        return map;
    }

    private void printMap(Map map) {
        Set<Map.Entry<String, List<Song>>> set = map.entrySet();
        StringBuilder dublicates = new StringBuilder();
        for (Map.Entry<String, List<Song>> me : set) {
            //Если список значений песен для очереденого ключа карты содердит больше одной песни, то значит, что для данной песни есть дубликаты - записываем в tringBuilder
            if (me.getValue().size() > 1) {
                dublicates.append("\n" + me.getValue().get(0).getArtist() + " " + me.getValue().get(0).getAlbum() + " " + me.getValue().get(0).getTitle() + ":\n");
                for (Song song : me.getValue()
                        ) {
                    dublicates.append("\t" + song.getPath() + "\n");

                }
            }
        }

        loggerForDublicatesWithoutChecksum.info(dublicates.toString());


    }
}

