public class Calculator {
    


    public static String extractWholeNumber(String number){
        int len=number.length();
        String result="";
        
        if(number.charAt(0)=='.'){
            return "";
        }
        for(int i=0;i<len && number.charAt(i)!='.';i++){
            result=number.substring(0,i+1);
            
        };
        int lenr=result.length();
        for(int j=0;j<lenr;j++){
            if(result.charAt(j)!='0'){
                return result.substring(0,result.length());}
            if(j==lenr-2 && result.charAt(lenr-1)=='0'){
                return "";}
        }
        return result.substring(0,result.length());
    }



    public static String extractDecimal(String number){
        int len=number.length();
        int dotindex=0;
        String result="";
        for(int i=0;i<len && number.charAt(i)!='.';i++){
                dotindex=i+1;
        }
        for(int i=dotindex;i<len;i++){
            result=number.substring(dotindex+1,i+1);
            };
  
            int lenr=result.length();
        for(int j=0;j<lenr;j++){
            if(result.charAt(j)!='0'){
                return result.substring(0,result.length());}
                if(lenr==1 && result.charAt(0)=='0')
                return "";
            if(j==lenr-2 && result.charAt(lenr-1)=='0'){
                return "";}
        } 
        return result.substring(0,result.length());
    }



    public static String prependZeros(String number, int numZeros){
        int len=number.length();
        if(numZeros<=0){
            return number;
        }
        StringBuilder result=new StringBuilder("");
        for(int i=numZeros;i!=0;i--){
            result.append('0');
        }
        result.append(number);
        int lenr=result.length();
        return result.substring(0,lenr);
    }



    public static String appendZeros(String number, int numZeros){
        int len=number.length();
        if(numZeros<=0){
            return number;
        }
        StringBuilder result=new StringBuilder("");
        result.append(number.substring(0,len));
        for(int i=numZeros;i!=0;i--){
            result.append('0');
        }
        return result.substring(0,result.length());
    }


    public static String formatResult(String number){
        if(number=="0"){
            return number;
        }
        int len=number.length();
        int des=0;
        for(int i=0;i<len;i++){
            if(number.charAt(i)=='.'){
                number=appendZeros(number,20);
                
                des=1;
                break;
            }
        }
        number=prependZeros(number,20);
        
        StringBuilder result=new StringBuilder("");
        
        String extra="";
        len=number.length();
        int indexdot=0;
        int numindex=0;
        //remove prepend
        for(int i=0;number.charAt(i)=='0';i++){
            numindex++;
        }
        result.append(number.substring(numindex,len));
        if(des==0){
            return result.substring(0,result.length());
        }
        for(int i=0;result.charAt(i)!='.';i++){
            indexdot++;
        }
        
        
        int numend=result.length()-1;
        if(des==1){
            for(int i=result.length()-1;i!=indexdot+1 && result.charAt(i)=='0';i--){
                numend--;
            }
        }
        
         if(result.charAt(0)=='.'){
            extra=result.substring(0,numend+1);
            result.setLength(0);
            result.append('0');
            result.append(extra.substring(0,extra.length()));
            return result.substring(0,result.length());
        }
        return result.substring(0,numend+1);
    }



    public static char addDigits(char firstDigit, char secondDigit, boolean carryIn){
        int digit=0;
        int bol=0;
        int fd=(int)firstDigit-48;
        int sd=(int)secondDigit-48;
        if(carryIn==true)
            bol=1;
        if(fd+sd+bol>=10)
            digit=fd+sd+bol-10;
        else
            digit=fd+sd+bol;
        return (char)(digit+48);
     }




    public static boolean carryOut(char firstDigit, char secondDigit, boolean carryIn){
        int bol=0;
        int fd=(int)firstDigit-48;
        int sd=(int)secondDigit-48;
        boolean out=false;
        if(carryIn==true){
            bol=1;
        }
        if(fd+sd+bol>=10){
            out=true;
        }
        return out;
        
    }



    public static String add(String firstNumber, String secondNumber){
        String first=formatResult(firstNumber);
        String second=formatResult(secondNumber);
        String num1=extractWholeNumber(first);
        String num2=extractWholeNumber(second);
        String des1=extractDecimal(first);
        String des2=extractDecimal(second);
        int len1=num1.length();
        int len2=num2.length();
        int lend1=des1.length();
        int lend2=des2.length();
        int pre=0;
        int app=0;

        if(len1>len2){
            for(int i=0;len1!=len2+i;i++){
                pre++;
            }
            num2=prependZeros(num2,pre);
        }
        if(len1<len2){
            for(int i=0;len1+i!=len2;i++){
                pre++;
            }
            num1=prependZeros(num1,pre);
        }

        //des
        if(lend1>lend2){
            for(int i=0;lend1!=lend2+i;i++){
                app++;
            }
            des2=appendZeros(des2,app);
        }
        if(lend1<lend2){
            for(int i=0;lend1+i!=lend2;i++){
                app++;
            }
            des1=appendZeros(des1,app);
        }
        
        len1=num1.length();
        len2=num2.length();
        lend1=des1.length();
        lend2=des2.length();

        //des calculate
        boolean next=false;
        StringBuilder add=new StringBuilder("");
        char digit;
        for(int i=lend1;i!=0;i--){
            digit=addDigits(des1.charAt(i-1),des2.charAt(i-1),next);
            next=carryOut(des1.charAt(i-1),des2.charAt(i-1), next);
            add.append(digit);
        }
        String totaldes = new StringBuilder(add).reverse().toString();
        add.setLength(0);
        digit='0';
        for(int i=len1;i!=0;i--){
            digit=addDigits(num1.charAt(i-1),num2.charAt(i-1),next);
            next=carryOut(num1.charAt(i-1),num2.charAt(i-1), next);
            add.append(digit);
        }
        if(next==true){
            add.append(1);
        }
        
        
        String totalnum=new StringBuilder(add).reverse().toString();
        StringBuilder total=new StringBuilder("");
        total.append(totalnum);
        total.append(".");
        total.append(totaldes);
        if(total.charAt(total.length()-1)=='.'){
            total.append('0');
        }
        String extra=total.substring(0,total.length());
        extra=formatResult(extra);
        return extra.substring(0,extra.length());

    }




    public static String multiply(String number, int numTimes){
        String jar="0";
        for(int i=0;i!=numTimes;i++){
            jar=add(jar,number);
        }
        if(jar=="0"){
            jar="0.0";
        }
        return jar.substring(0,jar.length());

    }
}
