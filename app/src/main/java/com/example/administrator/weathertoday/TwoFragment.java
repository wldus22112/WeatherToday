package com.example.administrator.weathertoday;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.URL;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class TwoFragment extends Fragment implements OnClickListener{


    private Document doc = null;
    View view;
    private String nowWeather;
    private String temp;
    private Button todayWeatherButton;
    private ImageView clothes;
    private float ptemp;
   private GetXMLTask parsing = new GetXMLTask();
    private int outer;
    private TextView tvTemp;
    private TextView tvClothes;
    private String nowClothes;
    private boolean sign;
    private TextView alam;

    private Random rn = new Random();



    public TwoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_two, container, false);

        //time = (TextView) view.findViewById(R.id.textview);
        // time.setText("oneFragment");
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/afonts.ttf");
        clothes = (ImageView) view.findViewById(R.id.imageView2);

        GetXMLTask task = new GetXMLTask();
        task.execute("http://www.kma.go.kr/wid/queryDFS.jsp?gridx=61&gridy=125");

        rn.setSeed(System.currentTimeMillis());

        todayWeatherButton = (Button) view.findViewById(R.id.todaybutton2);
        todayWeatherButton.setOnClickListener(this);

        parsing = new GetXMLTask();
       // parsing.execute("http://www.kma.go.kr/wid/queryDFS.jsp?gridx=61&gridy=125");

       // ptemp = Float.parseFloat(temp);

        tvClothes = (TextView) view.findViewById(R.id.clothes);
        tvTemp = (TextView) view.findViewById(R.id.temp2);
        //alam = (TextView) view.findViewById(R.id.outer);

        tvTemp.setTypeface(typeface);
        tvClothes.setTypeface(typeface);
       // alam.setTypeface(typeface);


        return view;
    }

    public void onClick(View view) {


        parsing.execute("http://www.kma.go.kr/wid/queryDFS.jsp?gridx=61&gridy=125");

        //temp = "10";
        sign = temp.startsWith("-"); //음수면 T 양수면 F

        if(sign == true){
            temp = temp.substring(1);
        }

        ptemp = Float.parseFloat(temp);
        clothesIconChange(ptemp);


        //tvTemp.setText("시작" + nowWeather + "끝");
    }


    private void  clothesIconChange(float input){


        if(input >=27 && sign == false){
            clothes.setImageResource(R.drawable.dress);
            nowClothes = "더워요!";
            outer = 0;
        }

        else if (input>=24 && input <27 &&sign == false){
            clothes.setImageResource(R.drawable.shirts);
            nowClothes = "조금 더워요";
            outer = 0;
        }

        else if (input>=20 && input <24 &&sign == false){


            int random =  rn.nextInt(2);

            if(random==0){
                clothes.setImageResource(R.drawable.shirt2);
            }
            else if(random==1){
                clothes.setImageResource(R.drawable.hood);
            }

            nowClothes = "따뜻!";
            outer = 0;
        }

        else if (input>=17 && input <20 &&sign == false){
            clothes.setImageResource(R.drawable.sweater);
            nowClothes = "슬슬 추워요";
            outer = 0;
        }

        else if (input>=11 && input <17 &&sign == false){
            int random =  rn.nextInt(3);

            if(random==0){
                clothes.setImageResource(R.drawable.jacket1);
            }
            else if(random==1){
                clothes.setImageResource(R.drawable.jacket2);
            }
            else if(random==2){
                clothes.setImageResource(R.drawable.jacket3);
            }
            nowClothes = "외투를 꼭 입어요~";
            outer = 1;
        }

        else if (input>=6 && input <11 &&sign == false){
            clothes.setImageResource(R.drawable.trenchcoat);
            nowClothes = "코트는 어떠세요?";
            outer = 1;
        }
        else if (input<6 &&sign == false){
            clothes.setImageResource(R.drawable.coat);
            nowClothes = "추워요! 겨울외투를 입어요ㅠㅠ";
            outer = 1;
        }

        else if (sign==true ){
            clothes.setImageResource(R.drawable.hat);
            nowClothes = "정말 추워요! 손 귀를 따뜻하게!";
            outer = 1;
            temp = "-"+temp;
        }





       tvTemp.setText("     "+temp + "°       ");
        tvClothes.setText(nowClothes);

    }





    //private inner class extending AsyncTask
    private class GetXMLTask extends AsyncTask<String, Void, Document> {

        @Override
        protected Document doInBackground(String... urls) {
            URL url;
            try {
                url = new URL(urls[0]);
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder(); //XML문서 빌더 객체를 생성
                doc = db.parse(new InputSource(url.openStream())); //XML문서를 파싱한다.
                doc.getDocumentElement().normalize();

            } catch (Exception e) {
                // Toast.makeText(getBaseContext(), "Parsing Error", Toast.LENGTH_SHORT).show();
            }
            return doc;
        }

        @Override
        protected void onPostExecute(Document doc) {

            String s = "";
            //data태그가 있는 노드를 찾아서 리스트 형태로 만들어서 반환
            NodeList nodeList = doc.getElementsByTagName("data");
            //data 태그를 가지는 노드를 찾음, 계층적인 노드 구조를 반환

            for(int i = 0; i< nodeList.getLength(); i++){

                //날씨 데이터를 추출
                s += "" +i + ":  ";
                Node node = nodeList.item(i); //data엘리먼트 노드
                Element fstElmnt = (Element) node;

                NodeList hourList = fstElmnt.getElementsByTagName("hour");
                s +="시간 = "+ hourList.item(0).getChildNodes().item(0).getNodeValue() +" , ";

                NodeList nameList  = fstElmnt.getElementsByTagName("temp");
                Element nameElement = (Element) nameList.item(0);
                nameList = nameElement.getChildNodes();
                s += "온도 = "+ ((Node) nameList.item(0)).getNodeValue() +" ,";


                NodeList websiteList = fstElmnt.getElementsByTagName("wfKor");
                //<wfKor>맑음</wfKor> =====> <wfKor> 태그의 첫번째 자식노드는 TextNode 이고 TextNode의 값은 맑음
                s += "날씨 = "+  websiteList.item(0).getChildNodes().item(0).getNodeValue() +"\n";

                if(i==0){
                   /* nowWeather = s;
                    if( websiteList.item(0).getChildNodes().item(0).getNodeValue() =="구름 많음"){

                    }*/

                    nowWeather = websiteList.item(0).getChildNodes().item(0).getNodeValue();
                    //weatherIconChange(websiteList.item(0).getChildNodes().item(0).getNodeValue());
                    temp = ((Node) nameList.item(0)).getNodeValue();
                }


            }

            //time.setText(s);
            // textviewWeather.setText(nowWeather);

            super.onPostExecute(doc);
        }


    }//end inner class - GetXMLTask

}