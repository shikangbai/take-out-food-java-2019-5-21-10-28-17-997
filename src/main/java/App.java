import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class App {
    private ItemRepository itemRepository;
    private SalesPromotionRepository salesPromotionRepository;
    private String s1 = null;
    private String s2 = null;
    public App(ItemRepository itemRepository, SalesPromotionRepository salesPromotionRepository) {
        this.itemRepository = itemRepository;
        this.salesPromotionRepository = salesPromotionRepository;
    }
    public String bestCharge(List<String> inputs) {
        //TODO: write code here
        Map<String,Integer> menu = new HashMap<String, Integer>();
        String str3 = "";
        for(String str:inputs){
            menu.put(str.substring(0,8), Integer.parseInt(str.substring(11)));
        }
        List<Item> list = itemRepository.findAll();
        List<SalesPromotion> list1 = salesPromotionRepository.findAll();
        System.out.println("============= 订餐明细 =============");
        str3 = "============= 订餐明细 =============\n";
        for(Item item:list){
            if(menu.containsKey(item.getId())){
                System.out.println(item.getName()+" x "+menu.get(item.getId())+" = " +(int)(item.getPrice()*menu.get(item.getId()))+"元");
                str3 += item.getName()+" x "+menu.get(item.getId())+" = " +(int)(item.getPrice()*menu.get(item.getId()))+"元\n";
            }
        }
        System.out.println("-----------------------------------");
        str3 +="-----------------------------------\n";
        int m1 = count(list,menu,1);
        int m2 = count(list,menu,2);
        if(m1<m2){
            System.out.println("使用优惠：");
            str3 += "使用优惠:\n";
            System.out.println(s1);
            str3 += s1+"\n";
            System.out.println("-----------------------------------");
            str3 += "-----------------------------------\n";
            System.out.println("总计："+m1+"元");
            str3+="总计："+m1+"元\n"+"===================================";
            System.out.println("====================================");
        }else{
            if(s2!=null){
                System.out.println("使用优惠：");
                str3 += "使用优惠:\n";
                System.out.println(s2);
                str3 += s2+"\n";
                System.out.println("-----------------------------------");
                str3 += "-----------------------------------\n";
            }
            System.out.println("总计："+m2+"元");
            str3+="总计："+m2+"元\n"+"===================================";
            System.out.println("====================================");
        }
        return str3;
    }
    public int count(List<Item> list,Map<String,Integer> map,int i){
        int money = 0;
        if(i == 1){
            int temMoney =0;
            String s="";
            for(Item item:list){
                if(map.containsKey(item.getId())){
                    temMoney += item.getPrice()*map.get(item.getId());
                    if(item.getId()=="ITEM0001" ||item.getId()=="ITEM0022" ){
                        s += item.getName().concat("，");
                        money += (item.getPrice()/2)*map.get(item.getId());
                    }else{
                        money += item.getPrice()*map.get(item.getId());
                    }
                    if(s.length()>0){
                        s1 = salesPromotionRepository.findAll().get(1).getDisplayName()+"("+s.substring(0,s.length()-1)+")，"+"省"+(temMoney-money)+"元";
                    }
                }
            }
            return money;
        }else if(i==2){
            for(Item item:list){
                if(map.containsKey(item.getId())){
                    money += item.getPrice()*map.get(item.getId());
                }
            }
            if(money>=30){
                money -= 6;
                s2 = salesPromotionRepository.findAll().get(0).getDisplayName()+"，"+"省6元";
                return money;
            }else {
                return money;
            }
        }
        return 0;
    }
}
