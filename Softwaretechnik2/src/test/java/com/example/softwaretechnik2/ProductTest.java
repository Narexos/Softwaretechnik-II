package com.example.softwaretechnik2;

import com.example.softwaretechnik2.model.Availability;
import com.example.softwaretechnik2.model.Product;
import com.example.softwaretechnik2.repositories.ProductRepository;
import com.example.softwaretechnik2.services.ProductService;
import com.sun.istack.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductTest extends Product {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository repo;

    @BeforeEach
    void setUp() {
        //Reihenfolge wichtig
        Product banane = new Product();
        banane.setCategory("Obst");

        Product kakao = new Product();
        kakao.setCategory("Getränk");
        kakao.setProductName("Kakao");

        Product kaffee2go = new Product();
        kaffee2go.setCategory("Getränk");

        Product salamibrötchen = new Product();
        salamibrötchen.setCategory("Belegtes Brötchen");

        repo.save(banane);
        repo.save(kakao);
        repo.save(kaffee2go);
        repo.save(salamibrötchen);
    }

    @Test
    public void shouldSaveUser() {
        Product product = new Product();
        Product savedProduct = repo.save(product);
        assertTrue(product.equals(savedProduct));
        repo.delete(product);
    }

    @Test
    void testFindProductsByCategory() {
        List<Product> getränke = repo.findProductsByCategory("Getränk");
        Stream<Product> getränkeStream = getränke.stream();
        assertTrue(getränkeStream.allMatch(x -> x.getCategory().equals("Getränk")));
    }

    @Test
    void testInitialProductNotBought() {
        Product product = new Product();
        assertFalse(repo.findProductById(4L).isBought());
        repo.delete(product);
    }

    @Test
    void testAutoGeneratedProductID() {
        assertEquals(4L, repo.findProductsByCategory("Belegtes Brötchen").get(0).getId());
    }

    @Test
    void testProductIsBought() {
        productService.buyProduct(repo.findProductById(1L));
        assertTrue(repo.findProductById(1L).isBought());
    }

    @Test
    void testFindProductByName() {
        Product product = new Product();
        product.setProductName("Hans");
        repo.save(product);
        Product hans = repo.findProductByProductName("Hans");
        assertEquals(hans.getProductName(), "Hans");
        repo.delete(product);
    }

    @Test
    void exceedInputsetproductName() {
        Product test = new Product();
        assertFalse(test.setProductName("12345"));
    }

    @Test
    void exceedInputsetCategory() {
        Product test = new Product();
        assertFalse(test.setCategory("12345"));
    }

    @Test
    void onlyNumbersInput() {
        Product test = new Product();
        assertTrue(test.setId(1L));
    }


    @Test
    void onlyNumbersinputsetPrice() {
        Product test = new Product();
        assertTrue(test.setPrice(3.01f));
    }

    @Test
    void onlyStringInputSetIngredients() {
        Product test = new Product();
        String ingredients = "Salamibrötchen, Schokobrötchen";
        assertTrue(test.setIngredients(ingredients));
    }

    @Test
    void isKoshertest() {
        Product test = new Product();
        assertFalse(test.isKosher());

        test.setKosher(true);
        assertTrue(test.isKosher());

        test.setKosher(false);
        assertFalse(test.isKosher());
    }

    /* outdated, image is now stored as byte[]
    @Test
    void imageTest() {
        Product product = new Product();
        String expectedImage = "example_image.jpg";
        product.setImage(expectedImage);
        String actualImage = product.getImage();
        assertEquals(expectedImage, actualImage);
    }*/


    @Test
    void availabilitySavedAsString() {
        Product test = new Product();
        test.setAvailability(Availability.FULL);
        repo.save(test);
        assertEquals("Voll", test.getAvailability());
        repo.delete(test);
    }

    @Test
    void properAvailabilityDisplayName() {
        Availability a = Availability.FULL;
        assertEquals("Voll", a.getDisplayValue());
    }

    @Test
    void testDeleteByProductName() {
        Product milch = new Product();
        milch.setProductName("Milch");
        repo.save(milch);
        repo.deleteProductByProductName("Milch");
        assertNull(repo.findProductByProductName("Milch"));
    }

    @Test
    void testCheckIfProductAlreadyExists() {
        Product p1 = new Product();
        p1.setProductName("Mehl");
        repo.save(p1);
        assertTrue(productService.checkIfProductExists(p1));
    }

    @Test
    void getCategoryTest() {
        Product product = new Product();
        String test = "Getränk";
        product.setCategory(test);
        assertEquals(test, product.getCategory());
    }


    @Test
    void getIdTest() {
        Product product = new Product();
        long id = 1L;
        product.setId(id);
        assertEquals(id, product.getId());
    }

    @Test
    void getProductNameTest() {
        Product product = new Product();
        String name = "Salamibrötchen";
        product.setProductName(name);
        assertEquals(name, product.getProductName());
    }

    @Test
    void getAllergensTest() {
        Product product = new Product();
        String allergens = "Laktose Intolerant";
        product.setAllergens(allergens);
        assertEquals(allergens, product.getAllergens());
    }

    @Test
    void getIngredientsTest() {
        Product product = new Product();
        String ingredient = "Milch";
        product.setIngredients(ingredient);
        assertEquals(ingredient, product.getIngredients());
    }

    @Test
    void getPriceTest() {
        Product product = new Product();
        float price = 2.0f;
        product.setPrice(price);
        assertEquals(price, product.getPrice());
    }

    @Test
    void setVeganTest() {
        Product product = new Product();
        product.setVegan(true);
        assertTrue(product.isVegan());
        product.setVegan(false);
        assertFalse(isVegan());
    }

    @Test
    void isEqualToTest()  {
        Product product = new Product();
        Product product1 = new Product();
        Product product2 = new Product();
        product.setProductName("Salamibrötchen");
        product1.setProductName("Salamibrötchen");
        product2.setProductName("Schokobrötchen");
        assertTrue(product.isEqualTo(product1));
        assertFalse(product2.isEqualTo(product));
    }

    @Test
    void setVegetarian() {
        Product product = new Product();
        Product product1 = new Product();
        product.setVegetarian(true);
        assertTrue(product.isVegetarian());
        assertFalse(product1.isVegetarian());
    }

    @Test
    void setHalal() {
        Product product = new Product();
        Product product1 = new Product();
        product.setHalal(true);
        product1.setHalal(false);
        assertTrue(product.isHalal());
        assertFalse(product1.isHalal());
    }

    @Test
    void isBoughtTest() {
        Product product = new Product();
        Product product1 = new Product();
        product.setBought(true);
        product1.setBought(false);
        assertTrue(product.isBought());
        assertFalse(product1.isBought());
    }

    @Test
    void base64ImageTest() {
        byte[] imageBytes = new byte[]{(byte) 0x89, (byte) 0x50, (byte) 0x4E, (byte) 0x47,
                (byte) 0x0D, (byte) 0x0A, (byte) 0x1A, (byte) 0x0A};
        Product myClass = new Product();
        myClass.setImage(imageBytes);
        String expectedBase64Image = "iVBORw0KGgo=";
        assertEquals(expectedBase64Image, myClass.getBase64Image());
    }

}
