package hr.javafx.production.devilla7.utils;

import hr.javafx.production.devilla7.enumeration.City;
import hr.javafx.production.devilla7.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class FileUtils {

    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    private static final String CATEGORIES_TEXT_FILE_NAME = "dat/categories.txt";
    private static final String ITEMS_TEXT_FILE_NAME = "dat/items.txt";
    private static final String ADDRESSES_TEXT_FILE_NAME ="dat/addresses.txt";
    private static final String FACTORIES_TEXT_FILE_NAME = "dat/factories.txt";
    private static final String STORES_TEXT_FILE_NAME = "dat/stores.txt";
    private static final Integer NUMBER_OF_LINES_FOR_CATEGORY = 3;
    public static List<Category> getCategoriesFromFile() {
        List<Category> categories = new ArrayList<>();
        File categoriesFile = new File(CATEGORIES_TEXT_FILE_NAME);
        try(BufferedReader reader = new BufferedReader(new FileReader(categoriesFile))){
            Optional<String> categoryIdOptional;
            while((categoryIdOptional = Optional.ofNullable(reader.readLine())).isPresent()){
                Optional<Category> newCategoryOptional = Optional.empty();
                Long id = Long.parseLong(categoryIdOptional.get());
                String categoryName = reader.readLine();
                String categoryDescription = reader.readLine();
                newCategoryOptional = Optional.of(new Category(id,categoryName,categoryDescription));
                if(newCategoryOptional.isPresent()){
                    categories.add(newCategoryOptional.get());
                }
            }
        } catch (FileNotFoundException e) {
            String message = "File not found!";
            logger.error(message,e);
            System.out.println(message);
        } catch (IOException e) {
            String message = "Dogodila se pogreska kod citanja datoteke";
            logger.error(message,e);
            System.out.println(message);
        }
        return categories;
    }

    public static List<Item> getItemsFromFile(List<Category> categoryList){
        List<Item> items = new ArrayList<>();
        File itemsFile = new File(ITEMS_TEXT_FILE_NAME);
        try(BufferedReader reader = new BufferedReader(new FileReader(itemsFile))){
            Optional<String> itemIdOptional;
            while((itemIdOptional = Optional.ofNullable(reader.readLine())).isPresent()){
                Optional<Item> newItemOptional;
                Long id = Long.parseLong(itemIdOptional.get());
                String name = reader.readLine();
                Long categoryId = Long.parseLong(reader.readLine());
                BigDecimal width = new BigDecimal(reader.readLine());
                BigDecimal height = new BigDecimal(reader.readLine());
                BigDecimal length = new BigDecimal(reader.readLine());
                BigDecimal productionCost = new BigDecimal(reader.readLine());
                BigDecimal sellingPrice = new BigDecimal(reader.readLine());
                Integer discount = Integer.parseInt(reader.readLine());
                Optional<Category> resultCategory = categoryList.stream()
                        .filter(category -> category.getId()==categoryId)
                        .findFirst();
                newItemOptional = Optional.of(new Item(id,name,resultCategory.get(),width,height,length,productionCost,sellingPrice,new Discount(discount)));
                if(newItemOptional.isPresent()){
                    items.add(newItemOptional.get());
                }
            }
        } catch (FileNotFoundException e) {
            String message = "File not found!";
            logger.error(message,e);
            System.out.println(message);
        } catch (IOException e) {
            String message = "Dogodila se pogreska kod citanja datoteke";
            logger.error(message,e);
            System.out.println(message);
        }
        return items;
    }

    public static List<Address> getAddressesFromFile(){
        List<Address> addresses = new ArrayList<>();
        File addressesFile = new File(ADDRESSES_TEXT_FILE_NAME);
        try (BufferedReader reader = new BufferedReader(new FileReader(addressesFile))){
            Optional<String> addressIdOptional;
            while((addressIdOptional=Optional.ofNullable(reader.readLine())).isPresent()){
                Optional<Address> newAddressOptional;
                Long id = Long.parseLong(addressIdOptional.get());
                String streetName = reader.readLine();
                String houseNumber = reader.readLine();
                City city = City.valueOf(reader.readLine());
                newAddressOptional = Optional.of(new Address(id,streetName,houseNumber,city));
                if(newAddressOptional.isPresent()){
                    addresses.add(newAddressOptional.get());
                }
            }
        } catch (FileNotFoundException e) {
            String message = "File not found!";
            logger.error(message,e);
            System.out.println(message);
        } catch (IOException e) {
            String message = "Dogodila se pogreska kod citanja datoteke";
            logger.error(message,e);
            System.out.println(message);
        }
        return addresses;
    }

    public static List<Factory> getFactoriesFromFile(List<Item> itemList, List<Address> addressList){
        List<Factory> factories = new ArrayList<>();
        File factoriesFile = new File(FACTORIES_TEXT_FILE_NAME);
        try(BufferedReader reader = new BufferedReader(new FileReader(factoriesFile))){
            Optional<String> factoryIdOptional;
            while((factoryIdOptional = Optional.ofNullable(reader.readLine())).isPresent()){
                Optional<Factory> newFactoryOptional;
                Long id = Long.parseLong(factoryIdOptional.get());
                String factoryName = reader.readLine();
                Long addressId = Long.parseLong(reader.readLine());
                Optional<Address> resultAddress = addressList.stream()
                        .filter(address -> address.getId()==addressId)
                        .findFirst();
                String[] itemIdsArray = reader.readLine().split(",");
                Set<Item> selectedItems = Arrays.stream(itemIdsArray)
                        .map(String::trim)
                        .map(Integer::parseInt)
                        .map(index -> itemList.get(index - 1))
                        .collect(Collectors.toSet());
                newFactoryOptional = Optional.of(new Factory(id,factoryName,resultAddress.get(),selectedItems));
                if(newFactoryOptional.isPresent()){
                    factories.add(newFactoryOptional.get());
                }
            }
        } catch (FileNotFoundException e) {
            String message = "File not found!";
            logger.error(message,e);
            System.out.println(message);
        } catch (IOException e) {
            String message = "Dogodila se pogreska kod citanja datoteke";
            logger.error(message,e);
            System.out.println(message);
        }
        return factories;
    }

    public static List<Store> getStoresFromFile(List<Item> itemList, List<Address> addressList){
        List<Store> stores = new ArrayList<>();
        File storesFile = new File(STORES_TEXT_FILE_NAME);
        try(BufferedReader reader = new BufferedReader(new FileReader(storesFile))){
            Optional<String> storeIdOptional;
            while((storeIdOptional = Optional.ofNullable(reader.readLine())).isPresent()){
                Optional<Store> newStoreOptional;
                Long id = Long.parseLong(storeIdOptional.get());
                String storeName = reader.readLine();
                String storeWebaddress = reader.readLine();
                Long addressId = Long.parseLong(reader.readLine());
                Optional<Address> resultAddress = addressList.stream()
                        .filter(address -> address.getId()==addressId)
                        .findFirst();
                String[] itemIdsArray = reader.readLine().split(",");
                Set<Item> selectedItems = Arrays.stream(itemIdsArray)
                        .map(String::trim)
                        .map(Integer::parseInt)
                        .map(index -> itemList.get(index - 1))
                        .collect(Collectors.toSet());
                newStoreOptional = Optional.of(new Store(id,storeName,storeWebaddress,resultAddress.get(),selectedItems));
                if(newStoreOptional.isPresent()){
                    stores.add(newStoreOptional.get());
                }
            }
        } catch (FileNotFoundException e) {
            String message = "File not found!";
            logger.error(message,e);
            System.out.println(message);
        } catch (IOException e) {
            String message = "Dogodila se pogreska kod citanja datoteke";
            logger.error(message,e);
            System.out.println(message);
        }
        return stores;
    }

    public static Long getNextCategoryId(){
        List<Category> categories = getCategoriesFromFile();
        Long lastCategoryId = categories.stream()
                .mapToLong(Category::getId)
                .max()
                .orElse(0);
        return lastCategoryId + 1;
    }

    public static void saveCategories(List<Category> categories){
        File categoryFile = new File(CATEGORIES_TEXT_FILE_NAME);
        try(PrintWriter pw = new PrintWriter(categoryFile)){
            for(Category category : categories){
                pw.println(category.getId());
                pw.println(category.getName());
                pw.println(category.getDescription());
            }
        } catch (IOException e) {
            String message = "Dogodila se pogreška kod zapisivanja podataka o kategorijama u datoteku!";
            logger.error(message, e);
            System.out.println(message);
        }
    }

    public static Long getNextItemId(){
        List<Category> categories = getCategoriesFromFile();
        List<Item> items = getItemsFromFile(categories);
        Long lastItemId = items.stream()
                .mapToLong(Item::getId)
                .max()
                .orElse(0);
        return lastItemId + 1;
    }

    public static void saveItems(List<Item> items) {
        File itemsFile = new File(ITEMS_TEXT_FILE_NAME);
        try(PrintWriter pw = new PrintWriter(itemsFile)){
            for(Item item : items){
                pw.println(item.getId());
                pw.println(item.getName());
                pw.println(item.getCategory().getId());
                pw.println(item.getWidth());
                pw.println(item.getHeight());
                pw.println(item.getLength());
                pw.println(item.getProductionCost());
                pw.println(item.getSellingPrice());
                pw.println(item.getDiscount().discountAmount());
            }
        } catch (IOException e) {
            String message = "Dogodila se pogreška kod zapisivanja podataka o itemima u datoteku!";
            logger.error(message, e);
            System.out.println(message);
        }
    }

    public static Long getNextFactoryId(){
        List<Category> categories = getCategoriesFromFile();
        List<Item> items = getItemsFromFile(categories);
        List<Address> addresses = getAddressesFromFile();
        List<Factory> factories = getFactoriesFromFile(items,addresses);
        Long lastFactoryId = factories.stream()
                .mapToLong(Factory::getId)
                .max()
                .orElse(0);
        return lastFactoryId + 1;
    }

    public static void saveFactories(List<Factory> factories) {
        File factoriesFile = new File(FACTORIES_TEXT_FILE_NAME);
        try(PrintWriter pw = new PrintWriter(factoriesFile)){
            for(Factory factory : factories){
                pw.println(factory.getId());
                pw.println(factory.getName());
                pw.println(factory.getAddress().getId());
                String selectedItems = factory.getItems().stream()
                        .map(item -> String.valueOf(item.getId()))
                        .collect(Collectors.joining(","));
                pw.println(selectedItems);
            }
        } catch (IOException e) {
            String message = "Dogodila se pogreška kod zapisivanja podataka o tvornicama u datoteku!";
            logger.error(message, e);
            System.out.println(message);
        }
    }

    public static Long getNextStoreId(){
        List<Category> categories = getCategoriesFromFile();
        List<Item> items = getItemsFromFile(categories);
        List<Address> addresses = getAddressesFromFile();
        List<Store> stores = getStoresFromFile(items,addresses);
        Long lastStoreId = stores.stream()
                .mapToLong(Store::getId)
                .max()
                .orElse(0);
        return lastStoreId + 1;
    }

    public static void saveStores(List<Store> stores) {
        File storesFile = new File(STORES_TEXT_FILE_NAME);
        try(PrintWriter pw = new PrintWriter(storesFile)){
            for(Store store : stores){
                pw.println(store.getId());
                pw.println(store.getName());
                pw.println(store.getWebAddress());
                pw.println(store.getAddress().getId());
                String selectedItems = store.getItems().stream()
                        .map(item -> String.valueOf(item.getId()))
                        .collect(Collectors.joining(","));
                pw.println(selectedItems);
            }
        } catch (IOException e) {
            String message = "Dogodila se pogreška kod zapisivanja podataka o trgovinama u datoteku!";
            logger.error(message, e);
            System.out.println(message);
        }
    }
}
