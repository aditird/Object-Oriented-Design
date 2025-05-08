public interface Codec{
  String encode(String input);
  String decode(String input);
}

public class RunLengthCodec implements Codec{
  private static final Pattern ENCODE_VALID_PATTERN = Pattern.compile("^[a-zA-Z]+$");
  private static final Pattern DECODE_VALID_PATTERN = Pattern.compile("([a-zA-Z]\\d*)+");

  public String encode(String input){
    if(!isValidForEncoding(input)){
      throw new IllegalArgumentException("Input is invalid");
    }

    StringBuilder sb = new StringBuilder();
    int count = 1;

    for (int i=1;i<input.length();i++){
      if(i < input.length() && input.charAt(i) == input.charAt(i-1)){
        count++;
      }else{
        sb.append(input.charAt(i-1));
      if (count > 1) sb.append(count);
      count=1;
      }

    }
    return sb.toString();
  }
}

public String decode(String input){
  if(!isValidForEncoding(input)){
    throw new IllegalArgumentException("Input is invalid");
  }

  StringBuilder sb = new StringBuilder();
  int i = 0;

  while(i < input.length()){
    char ch = input.charAt(i++);
    while(i < input.length() && Character.isDigit(input.charAt(i))){
      sb.append(input.charAt(i++));
    }
    int count = sb.length() > 0? Integer.parseInt(sb.toString()):1;
    sb.append(String.valueOf(ch).repeat(count));
  }
  return s.toString();

}

private boolean isValidForEncoding(String input){
  return input != null && ENCODE_VALID_PATTERN.matcher(input).matches();
}

private boolean isValidForEncoding(String input){
  return input != null && DECODE_VALID_PATTERN.matcher(input.matches());
}
