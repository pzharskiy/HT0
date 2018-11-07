package com.company.entities;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Album {
    private String title;
    private List<Song> songs = new ArrayList<Song>();
    /*Переменная для подсчетов дубликатов и их вывода в файл*/
    public static int countOfDublicates = 1;

    Album(File directoryItem) {
        AudioFile audioFile = null;
        try {
            audioFile = AudioFileIO.read(directoryItem);
        } catch (CannotReadException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TagException e) {
            e.printStackTrace();
        } catch (ReadOnlyFileException e) {
            e.printStackTrace();
        } catch (InvalidAudioFrameException e) {
            e.printStackTrace();
        }
        Tag tag = audioFile.getTag();
        //Если теги исполнителя пустые, то приваеваем ему имя "Unknown album"
        if (audioFile.getTag() == null || tag.getFirst(FieldKey.ALBUM).equals("")) {
            this.title = "Unknown album";
        } else {
            this.title = tag.getFirst(FieldKey.ALBUM);
        }
        songs.add(new Song(directoryItem));
    }

    void print() {
        System.out.println(title);
        for (Song song : songs
                ) {
            song.print();
        }

    }

    String printToFile() {
        StringBuilder html = new StringBuilder("" + title + "\n <p>\n ");
        for (Song song : songs
                ) {
            html.append(song.printToFile());
        }
        html.append("</p>\n");
        return html.toString();
    }

    String getTitle() {
        return title;
    }

    void addSong(File directoryItem) {
        songs.add(new Song(directoryItem));
    }

    public boolean equals(Object object) {
        if (object == this)
            return true;

     /* furniture ссылается на null */

        if (object == null)
            return false;

     /* Удостоверимся, что ссылки имеют тот же самый тип */

        if (!(getClass() == object.getClass()))
            return false;
        else {
            Album tmp = (Album) object;
            if (tmp.title.equals(this.title))
                return true;
            else
                return false;
        }
    }

    String findDublicates() {
        Map<String, List<Song>> map;
        map = createMap();
        return printMap(map);
    }

    private Map<String, List<Song>> createMap() {

        //на основе списка с песнями создаем множество, чтобы каждая песня встречались лишь один раз без дублирования
        HashSet<Song> dublicates = new LinkedHashSet<>(songs);
        // создаем карту. где ключ - имя трека, значения - список одноименных треков
        Map<String, List<Song>> map = new HashMap<>();

        for (Song song : dublicates
                ) {
            //считаем количество дублирования песен. Проходим по множеству и сравниваем с названиями из первоначального списка
            int occurrences = Collections.frequency(songs, new Song(song.getSong()));
            if (occurrences > 1) {
                //Если есть дубликаты, то создается отдельные подсписок, куда заносятся все дублирующиеся песни
                List<Song> subList = new ArrayList<>();
                for (Song dublicateSong : songs
                        ) {
                    if (dublicateSong.equals(song)) {
                        subList.add(dublicateSong);
                    }
                }
                //Ключ и список песен заносятся в карту
                map.put(song.getTitle(), subList);

            }
        }
        return map;
    }

    private String printMap(Map map) {
        StringBuilder dublicates = new StringBuilder();
        //Переводим карту в множество для вывода информации
        Set<Map.Entry<String, List<Song>>> set = map.entrySet();
        List<Song> list;
        if (!set.isEmpty()) {
            for (Map.Entry<String, List<Song>> me : set) {
                list = me.getValue();
                dublicates.append("\nДубликаты-" + countOfDublicates + ":\n");
                for (Song song : list
                        ) {

                    dublicates.append("\t" + song.getPath() + "\n");
                }
                countOfDublicates++;
            }
        }
        return dublicates.toString();
    }

    List<Song> findDublicatesWithoutCheckSum() {
        List<Song> list = new ArrayList<>();
        for (Song song : songs
                ) {
            list.add(song);
        }
        return list;
    }
}
