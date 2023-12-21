package hr.javafx.production.devilla7.utils;

import hr.javafx.production.devilla7.domain.*;
import hr.javafx.production.devilla7.enumeration.City;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class DatabaseUtils {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseUtils.class);

    private static final String DATABASE_FILE = "conf/database.properties";
    private static Connection connectToDatabase() throws SQLException, IOException {
        Properties svojstva = new Properties();
        svojstva.load(new FileReader(DATABASE_FILE));
        String urlBazePodataka = svojstva.getProperty("databaseUrl");
        String korisnickoIme = svojstva.getProperty("username");
        String lozinka = svojstva.getProperty("password");
        Connection veza = DriverManager.getConnection(urlBazePodataka,
                korisnickoIme,lozinka);
        return veza;
    }

    public static void insertNewCategoryToDatabase(Category category){
        try(Connection connection = connectToDatabase()){
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO CATEGORY (NAME, DESCRIPTION) VALUES(?,?)");
            preparedStatement.setString(1, category.getName());
            preparedStatement.setString(2,category.getDescription());
            preparedStatement.executeUpdate();
        }catch (SQLException | IOException ex) {
            String message = "Dogodila se pogreška kod dohvaćanja podataka iz baze!";
            logger.error(message, ex);
            System.out.println(message);
        }
    }

    public static void insertNewItemToDatabase(Item item){
        try(Connection connection = connectToDatabase()){
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO ITEM(CATEGORY_ID,NAME,WIDTH,HEIGHT,LENGTH,PRODUCTION_COST,SELLING_PRICE) VALUES (?,?,?,?,?,?,?)");
            preparedStatement.setLong(1,item.getCategory().getId());
            preparedStatement.setString(2,item.getName());
            preparedStatement.setBigDecimal(3,item.getWidth());
            preparedStatement.setBigDecimal(4,item.getHeight());
            preparedStatement.setBigDecimal(5,item.getLength());
            preparedStatement.setBigDecimal(6,item.getProductionCost());
            preparedStatement.setBigDecimal(7,item.getSellingPrice());
            preparedStatement.executeUpdate();
        }catch (SQLException | IOException ex) {
            String message = "Dogodila se pogreška kod dohvaćanja podataka iz baze!";
            logger.error(message, ex);
            System.out.println(message);
        }
    }

    private static Category getCategoryFromResultSet(ResultSet categoriesResultSet)
            throws SQLException
    {
        Long categoryId = categoriesResultSet.getLong("ID");
        String categoryName = categoriesResultSet.getString("NAME");
        String categoryDescription = categoriesResultSet.getString("DESCRIPTION");
        return new Category(categoryId,categoryName,categoryDescription);
    }


    public static List<Category> getAllCategoriesFromDatabase() {
        List<Category> categories = new ArrayList<>();
        try (Connection connection = connectToDatabase()) {
            Statement sqlStatement = connection.createStatement();
            ResultSet resultSet = sqlStatement.executeQuery("SELECT * FROM CATEGORY");
            while(resultSet.next()){
                Category newCategory = getCategoryFromResultSet(resultSet);
                categories.add(newCategory);
            }
        }
        catch (SQLException | IOException ex) {
            String message = "Dogodila se pogreška kod dohvaćanja podataka iz baze!";
            logger.error(message, ex);
            System.out.println(message);
        }
        return categories;
    }

    public static List<Item> getAllItemsFromDatabase(List<Category> categoryList) {
        List<Item> items = new ArrayList<>();
        try (Connection connection = connectToDatabase()) {
            Statement sqlStatement = connection.createStatement();
            ResultSet resultSet = sqlStatement.executeQuery("SELECT * FROM ITEM");
            while(resultSet.next()){
                Item newItem = getItemFromResultSet(resultSet,categoryList);
                items.add(newItem);
            }
        } catch (SQLException | IOException ex) {
            String message = "Dogodila se pogreška kod dohvaćanja podataka iz baze!";
            logger.error(message, ex);
            System.out.println(message);
        }
        return items;
    }

    private static Item getItemFromResultSet(ResultSet itemsResultSet,List<Category> categoryList)
            throws SQLException {
        Long itemId = itemsResultSet.getLong("ID");
        Long categoryId = itemsResultSet.getLong("CATEGORY_ID");
        String itemName = itemsResultSet.getString("NAME");
        BigDecimal width = itemsResultSet.getBigDecimal("WIDTH");
        BigDecimal height = itemsResultSet.getBigDecimal("HEIGHT");
        BigDecimal length = itemsResultSet.getBigDecimal("LENGTH");
        BigDecimal productionCost = itemsResultSet.getBigDecimal("PRODUCTION_COST");
        BigDecimal sellingPrice = itemsResultSet.getBigDecimal("SELLING_PRICE");
        Optional<Category> resultCategory = categoryList.stream()
                .filter(category -> category.getId()==categoryId)
                .findFirst();
        return new Item(itemId, itemName, resultCategory.get(), width, height, length, productionCost, sellingPrice, new Discount(0));
    }

    public static List<Address> getAddressesFromDatabase() {
        List<Address> addresses = new ArrayList<>();
        try (Connection connection = connectToDatabase()) {
            Statement sqlStatement = connection.createStatement();
            ResultSet resultSet = sqlStatement.executeQuery("SELECT * FROM ADDRESS");
            while (resultSet.next()) {
                Address newAddress = getAddressFromResultSet(resultSet);
                addresses.add(newAddress);
            }
        } catch (SQLException | IOException ex) {
            String message = "An error occurred while retrieving data from the database!";
            logger.error(message, ex);
            System.out.println(message);
        }
        return addresses;
    }
    private static Address getAddressFromResultSet(ResultSet addressResultSet) throws SQLException {
        Long addressId = addressResultSet.getLong("ID");
        String street = addressResultSet.getString("STREET");
        String houseNumber = addressResultSet.getString("HOUSE_NUMBER");
        String city = addressResultSet.getString("CITY");
        int postalCode = addressResultSet.getInt("POSTAL_CODE");
        return new Address(addressId, street, houseNumber, City.valueOf(city.toUpperCase()));
    }

    public static List<Factory> getFactoriesFromDatabase(List<Address> addressList,List<Category> categoryList) {
        List<Factory> factories = new ArrayList<>();
        try (Connection connection = connectToDatabase()) {
            Statement sqlStatement = connection.createStatement();
            ResultSet resultSet = sqlStatement.executeQuery("SELECT * FROM FACTORY");
            while (resultSet.next()) {
                Factory newFactory = getFactoryFromResultSet(resultSet,addressList,categoryList);
                factories.add(newFactory);
            }
        } catch (SQLException | IOException ex) {
            String message = "An error occurred while retrieving data from the database!";
            logger.error(message, ex);
            System.out.println(message);
        }
        return factories;
    }

    private static Factory getFactoryFromResultSet(ResultSet factoryResultSet,List<Address> addressList,List<Category> categoryList) throws SQLException {
        Long factoryId = factoryResultSet.getLong("ID");
        String factoryName = factoryResultSet.getString("NAME");
        Long addressId = factoryResultSet.getLong("ADDRESS_ID");
        Optional<Address> resultAddress = addressList.stream()
                .filter(address -> address.getId()==addressId)
                .findFirst();
        List<Item> factoryItemsList = getItemsForFactory(factoryId,categoryList);
        Set<Item> factoryItems = new HashSet<>(factoryItemsList);
        return new Factory(factoryId, factoryName, resultAddress.get(),factoryItems);
    }

    public static List<Item> getItemsForFactory(Long factoryId,List<Category> categoryList) {
        List<Item> items = new ArrayList<>();

        try (Connection connection = connectToDatabase()) {
            String sqlQuery = "SELECT * FROM FACTORY_ITEM FI, ITEM I " +
                    "WHERE FI.FACTORY_ID = ? AND FI.ITEM_ID = I.ID";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
                preparedStatement.setLong(1, factoryId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Item newItem = getItemFromResultSet(resultSet,categoryList);
                        items.add(newItem);
                    }
                }
            }
        } catch (SQLException | IOException ex) {
            String message = "Error occurred while retrieving items for factory from the database!";
            logger.error(message, ex);
            System.out.println(message);
        }

        return items;
    }

    public static List<Item> getItemsForStore(Long storeId,List<Category> categoryList) {
        List<Item> items = new ArrayList<>();

        try (Connection connection = connectToDatabase()) {
            String sqlQuery = "SELECT * FROM STORE_ITEM SI, ITEM I WHERE SI.STORE_ID = ? AND SI.ITEM_ID = I.ID";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
                preparedStatement.setLong(1, storeId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Item newItem = getItemFromResultSet(resultSet,categoryList);
                        items.add(newItem);
                    }
                }
            }
        } catch (SQLException | IOException ex) {
            String message = "Error occurred while retrieving items for factory from the database!";
            logger.error(message, ex);
            System.out.println(message);
        }

        return items;
    }

    public static List<Store> getStoresFromDatabase(List<Category> categoryList) {
        List<Store> stores = new ArrayList<>();
        try (Connection connection = connectToDatabase()) {
            Statement sqlStatement = connection.createStatement();
            ResultSet resultSet = sqlStatement.executeQuery("SELECT * FROM STORE");
            while (resultSet.next()) {
                Store newStore = getStoreFromResultSet(resultSet,categoryList);
                stores.add(newStore);
            }
        } catch (SQLException | IOException ex) {
            String message = "An error occurred while retrieving data from the database!";
            logger.error(message, ex);
            System.out.println(message);
        }
        return stores;
    }

    private static Store getStoreFromResultSet(ResultSet storeResultSet,List<Category> categoryList) throws SQLException {
        Long storeId = storeResultSet.getLong("ID");
        String storeName = storeResultSet.getString("NAME");
        String webAddress = storeResultSet.getString("WEB_ADDRESS");
        Address address = new Address(Long.parseLong("0"),"","",City.ANY_CITY);
        List<Item> storeItemsList = getItemsForStore(storeId,categoryList);
        Set<Item> storeItems = new HashSet<>(storeItemsList);
        return new Store(storeId,storeName,webAddress,address,storeItems);
    }
}
