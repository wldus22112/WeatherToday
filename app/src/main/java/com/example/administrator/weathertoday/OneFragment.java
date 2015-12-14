package com.example.administrator.weathertoday;

import android.app.Activity;
import android.content.Context;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class OneFragment extends Fragment implements OnClickListener  {

    private Document doc = null;
    private LinearLayout layout = null;
    private String nowWeather;
    private ImageView weatherIcon;
    private TextView tv;
     View view;
    private Button todayButton;
    private Context mContext;
    private TextView time;
    private String temp;
    private TextView weather;
    private  TextView tempT;
     private GetXMLTask task;

   public OneFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       // GetXMLTask task = new GetXMLTask();
       // task.execute("http://www.kma.go.kr/wid/queryDFS.jsp?gridx=61&gridy=125");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_one, container, false);

        //time = (TextView) view.findViewById(R.id.textview);
       // time.setText("oneFragment");
        weather = (TextView) view.findViewById(R.id.weather);
      //  (TextView) weather.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/a옛날목욕탕.ttf"));
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/afonts.ttf");

        tempT = (TextView) view.findViewById(R.id.temp);


        weather.setTypeface(typeface);
        tempT.setTypeface(typeface);


        weatherIcon = (ImageView) view.findViewById(R.id.imageView);


        task = new GetXMLTask();
        task.execute("http://www.kma.go.kr/wid/queryDFS.jsp?gridx=61&gridy=125");



        todayButton = (Button) view.findViewById(R.id.todaybutton);
        todayButton.setOnClickListener(this);

        return view;
    }

    public void onClick(View view) {
        weatherIconChange(nowWeather);
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


    private void  weatherIconChange(String input){

       weather.setText(input);




        if(input.equals("구름 많음")){
            weatherIcon.setImageResource(R.drawable.cloud);
        }


        else if(input.equals("맑음")){
            weatherIcon.setImageResource(R.drawable.sunny);

        }

        else if(input.equals("구름 조금")){
            weatherIcon.setImageResource(R.drawable.cloud2);

        }

        else if(input.equals("흐림")){
            weatherIcon.setImageResource(R.drawable.cloud);
        }

        else if(input.equals("비")){
            weatherIcon.setImageResource(R.drawable.rainy);

        }

        else if(input.equals("눈")){
            weatherIcon.setImageResource(R.drawable.snow);
        }
        else if(input.equals("소나기")){
            weatherIcon.setImageResource(R.drawable.rain);
        }

        else if(input.equals("연무")||input.equals("박무")||input.equals("안개")){
            weatherIcon.setImageResource(R.drawable.foggy);
        }

        else if(input.equals("가끔 비")||input.equals("한때 비")||input.equals("가끔 눈")||input.equals("한때 눈")||input.equals("가끔 비 또는 눈")
                ||input.equals("한때 비 또는 눈")||input.equals("가끔 눈 또는 비")||input.equals("한때 눈 또는 비")){
            weatherIcon.setImageResource(R.drawable.sometime);
        }

        else if(input.equals("연무")||input.equals("박무")||input.equals("안개")){
            weatherIcon.setImageResource(R.drawable.foggy);
        }

        else if(input.equals("천둥번개")){
            weatherIcon.setImageResource(R.drawable.thunder2);
        }

        tempT.setText("     "+temp + "°       ");

    }



}
