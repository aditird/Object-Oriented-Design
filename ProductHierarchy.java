//price and prime eligibility
//query by category and prime
//easily extensible


public class Product{
  String name;
  double price;
  boolean isPrimeEligible;
  int shippingSpeed;

  public Product(String name, double price, boolean isPrimeEligible){
    this.name=name;
    this.price=price;
    this.isPrimeEligible = isPrimeEligible;
    this.shippingSpeed = shippingSpeed;
  }

  public String toString(){
    return name +" $"+price+" Prime: "+isPrimeEligible;
  }
}

public class Category{
  String name;
  List<Product> products = new ArrayList<>();
  List<Category> subCategories = new ArrayList<>();

  public Category(String name){
    this.name = name;
  }
  public void addProduct(Product product){
    products.add(product);
  }
  public void addsubcategory(Category category){
    subCategories.add(category);
  }
}

interface Filter{
  boolean apply(Product product);
}

class MaxPriceFilter implements Filter{
  double maxPrice;
  public MaxPriceFilter(double maxPrice){
    this.maxPrice = maxPrice;
  }
  public boolean apply(Product product){
    return product.price <= maxPrice;
  }
}
class ShippingSpeedFilter implements filter{
  private final String desiredSpeed;
  public ShippingSpeedFilter(String desiredSpeed){
    desiredSpeed = desiredSpeed;
  }
  public boolean apply(Product product){
    return product.shippingSpeed.equalsIgnoreCase(desiredSpeed);
  }
}

class FilterFactory{
  public static Filter createFilter(String fiterName, String value){
    switch(fiterName.toLowerCase()){
      case "maxprice":
      return new MaxPriceFilter(Integer.parseInt(value));
      case "shippingSpeed":
      return new ShippingSpeedFilter(value);
    }

  }
}

class ProductSearcher{
  public List<Product> search(Category root, String targetCategoryName, List<filer> filters){
    Category targetCategory = findCategory(root, targetCategoryName);
    if(targetCategory == null) return collections.emptyList();

    List<Product> results = new ArrayList<>();
    dfsCollectProducts(targetCategory, filters, results);
    return results;
  }

  private Category findCategory(Category root, String name){
    if(root.name.equalsIgnoreCase(name)) return root;
    for(Category sub: root.subCategories){
      Category found = findCategory(sub);
      if(found != null) return found;
    }
    return null;
  }

  private void dfsCollectProducts(Category category, List<filter> filters, List<Product> results){
    for(Product product: category.products){
      boolean valid = true;
      for(Filter f: filters){
        if(!f.apply(product)){
          valid = false;
          break;
        }
      }
      if(valid) results.add(product);
    }

    for(Category sub: category.subCategories){
      dfsCollectProducts(sub, filters, results);
    }

  }
}

public class Main {
    public static void main(String[] args) {
        // Build category tree
        Category root = new Category("Clothing");
        Category women = new Category("Women");
        Category dresses = new Category("Dresses");
        root.addSubcategory(women);
        women.addSubcategory(dresses);

        // Add products
        dresses.addProduct(new Product("Red Dress", 20, true));
        dresses.addProduct(new Product("Blue Dress", 50, false));
        dresses.addProduct(new Product("Black Dress", 70, true));

        // Search with filters
        ProductSearcher searcher = new ProductSearcher();
        List<Filter> filters = new ArrayList<>();
        filters.add(FilterFactory.createFilter("maxprice", "50"));
          // max price filter

        List<Product> results = searcher.search(root, "Dresses", filters);
        System.out.println("Search results: ");
        for (Product p : results) {
            System.out.println(p);
        }
    }
}
