package com.company.entities;

import org.apache.log4j.Logger;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Artist {
    private String name;
    private List<Album> albums = new ArrayList<Album>();

    String getName() {
        return name;
    }

    Artist(File directoryItem) {
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
        //Если теги исполнителя пустые, то приваеваем ему имя "Unknown artist"
        if (audioFile.getTag()==null || tag.getFirst(FieldKey.ARTIST).equals(""))
        {
            this.name="Unknown artist";
        }
        else {
            this.name = tag.getFirst(FieldKey.ARTIST);
        }
        albums.add(new Album(directoryItem));
    }

    void print() {
        System.out.println(name);
        for (Album album : albums
                ) {
            album.print();
        }
    }

    String printToFile() {
        StringBuilder html= new StringBuilder(" <h3>\n " + name + " </h3>\n <h4>\n ");
        for (Album album : albums
                ) {
            html.append(album.printToFile());
        }
        html.append("</h4>\n");
        return html.toString();
    }

    void addAlbum(File directoryItem) {
        //Если альбом существует, то запрашиваем альбом и добавляем в него песню
        if (albums.contains(new Album(directoryItem))) {
            getAlbum(directoryItem).addSong(directoryItem);
        }
        //Иначе добавляем новый альбом
        else {
            albums.add(new Album(directoryItem));
        }
    }

    Album getAlbum(File directoryItem) {
        Album checkingAlbum=new Album(directoryItem);
        //Проверка, существует ли альбом переданной песни (файла)
        for (Album album : albums
                ) {
            if (album.getTitle().equals(checkingAlbum.getTitle())) {
                return album;
            }
        }
        return null;
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
            Artist tmp = (Artist) object;
            if (tmp.name.equals(this.name))
                return true;
            else
                return false;
        }
    }

    String findDublicates() {
        StringBuilder dublicates = new StringBuilder();
        for (Album album : albums
                ) {
            dublicates.append(album.findDublicates());
        }
        return  dublicates.toString();
    }

    List<Song> findDublicatesWithoutCheckSum() {
        List<Song> list = new ArrayList<Song>();
        //Вернуть список со всеми песнями и распределить их по карте по ключу - контрольной сумме
        for (Album album : albums
                ) {
            list.addAll(album.findDublicatesWithoutCheckSum());
        }
        return list;
    }
}
