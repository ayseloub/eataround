package com.example.foodaround.ui;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public class NoteDiffCallback extends DiffUtil.Callback {
    private final List<Note> mOldNoteList;
    private final List<Note> mNewNoteList;

    public NoteDiffCallback(List<Note> oldNoteList, List<Note> newNoteList) {
        this.mOldNoteList = oldNoteList;
        this.mNewNoteList = newNoteList;
    }

    @Override
    public int getOldListSize() {
        return mOldNoteList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewNoteList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldNoteList.get(oldItemPosition).getId().equals(mNewNoteList.get(newItemPosition).getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final Note oldNote = mOldNoteList.get(oldItemPosition);
        final Note newNote = mNewNoteList.get(newItemPosition);
        return oldNote.getTitle().equals(newNote.getTitle()) && oldNote.getDescription().equals(newNote.getDescription()) && oldNote.getImagePath().equals(newNote.getImagePath());
    }
}
