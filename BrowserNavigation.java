//forward button - current page from forward goes to backwardStack
//backward button - current page goes from backward to forward
//new page - new pushed to backward stack and forward stak should be cleared.
public class Page{
  String url;
  String content;
  public Page(String url, String content){
    this.url=url;
    this.content=content;
  }
  public String getUrl(){
    return url;
  }
  public String getContent(){
    return content;
  }
}

public class Browser{
  Stack<Page> forwardStack;
  Stack<Page> backwardStack;
  Page currentPage;

  public Browser(){
    forwardStack = new Stack<>();
    backwardStack = new Stack<>();
    currentPage = null;
  }

  public Page forward(){
    if(forwardStack.isEmpty()){
      System.out.println("No page to go forward.");
      return null;
    }
    Page currentPage = forwardStack.pop();
    backwardStack.push(currentPage);
    return currentPage;
  }

  public Page forward(){
    if(backwardStack.isEmpty()){
      system.out.println("No page to forward");
      return null;
    }
    Page currentPage = forwardStack.pop();
    backwardStack.push(currentPage);
    return currentPage;
  }

  public void visit(String url, String content){
    currentPage = new Page(url, content);
    backwardStack.push(page);
    forwardStack.clear();
  }

  public String getCurrentPage(){
    return currentPage != null ? currentPage.getUrl():"No page to return!"
  }
}

public class Main{
  public static void main(String[] args){
    Browser browser = new Browser();
    browser.visit("page1.com", "<html> Page 1 content</html>");
    browser.visit("page2.com", "<html> Page 2 content</html>");
    browser.back();
    System.out.println("get current page - "+browser.getCurrentPage);
    browser.forward();
    System.out.println("get current page - "+browser.getCurrentPage);

  }
}
