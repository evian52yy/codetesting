package com.streamlinity.ct.restService.challenge;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.streamlinity.ct.model.Item;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
 * Provide your implementation of the SearchSvcImpl here.
 * Also annotate your methods with Rest end point wrappers as required in the problem statement
 *
 * You can create any auxiliary classes or interfaces in this package if you need them.
 *
 * Do NOT add annotations as a Bean or Component or Service.   This is being handled in the custom Config class
 * PriceAdjustConfiguration
 */

public class SearchSvcImpl implements SearchSvcInterface {

    private String itemPriceJsonFileName;
    private File itemPriceJsonFile;

    @Override
    public void init(String itemPriceJsonFileName) {
        this.itemPriceJsonFileName = itemPriceJsonFileName;
    }

    @Override
    public void init(File itemPriceJsonFile) {
        try {
            itemPriceJsonFile.createNewFile();
            this.itemPriceJsonFile = itemPriceJsonFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Item> getItems() {
        List<Item> itemList = new ArrayList<>();
        Gson gson = new Gson();
        try {
            itemList = gson.fromJson(new FileReader(itemPriceJsonFile), List.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return itemList;
    }

    @Override
    public List<Item> getItems(String category) {
        List<Item> itemList = new ArrayList<>();
        Gson gson = new Gson();
        try {
            Type typeOfT = TypeToken.getParameterized(List.class, Item.class).getType();
            itemList = gson.fromJson(new FileReader(itemPriceJsonFile), typeOfT);
            itemList = itemList.stream().filter(s -> s.getCategory_short_name().equals(category)).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return itemList;
    }

    @Override
    public List<Item> getItem(String itemShortName) {
        List<Item> itemList = new ArrayList<>();
        Gson gson = new Gson();
        try {
            Type typeOfT = TypeToken.getParameterized(List.class, Item.class).getType();
            itemList = gson.fromJson(new FileReader(itemPriceJsonFile), typeOfT);
            itemList = itemList.stream().filter(s -> s.getShort_name().equals(itemShortName)).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return itemList;
    }
}
