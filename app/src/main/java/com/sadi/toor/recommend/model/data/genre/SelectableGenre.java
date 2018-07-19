package com.sadi.toor.recommend.model.data.genre;

public class SelectableGenre extends Genre {

    private boolean isSelected = false;

    public SelectableGenre(Genre item, boolean isSelected) {
        super(item.getGenreId(), item.getGenreName());
        this.isSelected = isSelected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
