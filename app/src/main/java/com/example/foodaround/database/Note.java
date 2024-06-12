package com.example.foodaround.database;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Note implements Parcelable {
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        private int id;

        @ColumnInfo(name = "title")
        private String title;

        @ColumnInfo(name = "description")
        private String description;

        @ColumnInfo(name = "date")
        private String date;

        @ColumnInfo(name = "image_path")
        private String imagePath;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getImagePath() {
            return imagePath;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.title);
            dest.writeString(this.description);
            dest.writeString(this.date);
            dest.writeString(this.imagePath); // Menambahkan URI gambar ke dalam parcel
        }

        @Ignore
        public Note() {
        }

        public Note(String title, String description, String date, String imagePath) {
            this.title = title;
            this.description = description;
            this.date = date;
            this.imagePath = imagePath;
        }

        private Note(Parcel in) {
            this.id = in.readInt();
            this.title = in.readString();
            this.description = in.readString();
            this.date = in.readString();
            this.imagePath = in.readString(); // Membaca URI gambar dari parcel
        }

        public static final Creator<Note> CREATOR = new Creator<Note>() {
            @Override
            public Note createFromParcel(Parcel source) {
                return new Note(source);
            }

            @Override
            public Note[] newArray(int size) {
                return new Note[size];
            }
        };
}
