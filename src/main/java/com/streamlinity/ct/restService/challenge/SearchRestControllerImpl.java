package com.streamlinity.ct.restService.challenge;

import com.streamlinity.ct.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.util.List;

/*
 * This controller needs to expose the following rest endpoints.  You need to fill in the implementation here
 *
 * Required REST Endpoints
 *
 *      /item                       Get all items
 *      /item?category=C            Get all items in category specified by Category shortName
 *      /item/{itemShortName}       Get item that matches the specified Item shortName
 */

@Profile("default")
@RestController
public class SearchRestControllerImpl {

    @Autowired
    private SearchSvcInterface searchSvc;

    @Autowired
    ApplicationContext applicationContext;

//    @GetMapping("/item")
//    public List<Item> getAllItems() {
//        return searchSvc.getItems();
//    }

    @GetMapping("/item")
    public List<Item> getAllItemsInCategory(@RequestParam(required = false) String category) {
        File itemFile = null;
        try {
            itemFile = applicationContext.getResource("classpath:itemPrices.json").getFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        searchSvc.init(itemFile);
        if (category == null) {
            return searchSvc.getItems();
        }
        return searchSvc.getItems(category);
    }

    @GetMapping("/item/{itemShortName}")
    public List<Item> getItemWithShortName(@PathVariable("itemShortName") String itemShortName) {
        File itemFile = null;
        try {
            itemFile = applicationContext.getResource("classpath:itemPrices.json").getFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        searchSvc.init(itemFile);
        return searchSvc.getItem(itemShortName);
    }
}
