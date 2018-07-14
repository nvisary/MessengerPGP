package com.summerproject.messenger.ui.test;


import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class MainScreenController {
    public ListView listView;

    public void setItemsToListView(ObservableList list) {
        listView.setItems(list);
    }
}
