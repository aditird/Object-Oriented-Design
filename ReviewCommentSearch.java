

abstract class ContentNode{
  private String id;
  private String text;
  private String langauge;
  private List<Comment> comments;

  public ContentNode(String id, String text, String langauge){
    this.id=id;
    this.text=text;
    this.langauge=langauge;
    this.comments = new ArrayList<>();
  }

  public String getId(){
    return id;
  }

  public String getText(){
    return text;
  }

  public String getLanguage(){
    return language;
  }

  public List<Comment> getComments(){
    return comments;
  }

  public void addComment(Comment Comment){
    comments.add(comment);
  }
}

class Review extends ContentNode{
  public Review(String id, String text, String language){
    super(id,text,language);
  }
}

class Comment extends ContentNode{
  public Comment(String id, String text, String langauge){
    super(id,text,langauge);
  }
}


class SearchReviews{
  public List<ContentNode> search(List<Review> reviews, SearchCritera criteria){
    List<ContentNode> result = new ArrayList<>();
    for(Review review: reviews){
      searchRecursive(review, criteria, result);
    }
    return result;
  }

  private void searchRecursive(ContentNode node, SearchCritera criteria, List<ContentNode> result){
    if(criteria.matches(node)){
      result.add(node);
    }
    for(Comment comment:node.getComments){
      searchRecursive(comment, criteria, result);
    }
  }
}

interface SearchCriteria{
  boolean matches(ContentNode node);
}


class TextSearchCriteria implements SearchCritera{
  private String searchText;
  public TextSearchCriteria(String text){
    this.searchText = searchText.toLowerCase();
  }

  public boolean matches(ContentNode node){
    return node.getText().toLowerCase().contains(searchText);
  }
}

class LanguageSearchCriteria implements SearchCriteria {
    private String language;

    public LanguageSearchCriteria(String language) {
        this.language = language;
    }

    @Override
    public boolean matches(ContentNode node) {
        return node.getLanguage().equalsIgnoreCase(language);
    }
  }

  public class main{
    public static void main(String[] args){
      List<Review> reviews = new ArrayList<>();

Review r1 = new Review("r1", "Great product", "en");
r1.addComment(new Comment("c1", "Agree with the review!", "en"));
r1.addComment(new Comment("c2", "Produit incroyable", "fr"));

reviews.add(r1);

SearchReviews searchAPI = new SearchReviews();
List<ContentNode> found = searchAPI.search(reviews, new TextSearchCriteria("great"));

for (ContentNode node : found) {
    System.out.println("Found: " + node.getText() + " in language " + node.getLanguage());
}

    }
  }
