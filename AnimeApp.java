package assignment;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.DefaultCaret;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AnimeApp {

    private String [] lblNames = {"ID :"," Anime Name :", "Description :","Director :","Producer :","Release Date :","Rt Score :"};
    private JFrame frame;
    private JLabel lblTitle;
    private JLabel lblContent;
    private JPanel animePnl;
    private JPanel showAnimePnl;
	private JTable animeTbl;
	private JScrollPane scrl;
	private JSONArray jsnArr = new JSONArray();

    public static void main(String args[])throws Exception{
    	new AnimeApp();
    }
    public AnimeApp(){
    	init();
    }
    public void init(){
    	frame = new JFrame("Ghibliapi");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setSize(800,500);
    	
        animePnl = new JPanel();
        showAnimePnl = new JPanel();
        lblTitle = new JLabel("Studio Ghibliapi");
		JTextField lblID = new JTextField();
		JTextField lblAnimeName = new JTextField();
		JTextField lbldirector = new JTextField();
		JTextField lblproducer = new JTextField();
		JTextField lblrelease_date = new JTextField();
		JTextField lblrt_score = new JTextField(); 
		
		JTextArea lblDesc = new JTextArea();
		DefaultCaret caret = (DefaultCaret)lblDesc.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		JScrollPane scrollPane = new JScrollPane();
		
		animeTbl = new JTable();
		scrl = new JScrollPane();
        
        lblTitle.setBounds(250,20,100,30);
        animePnl.setBounds(320 , 50, 400, 400);
        showAnimePnl.setBounds(10, 50, 300, 400);

        animePnl.setBorder(new TitledBorder(null, "View Anime Detail", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		animePnl.setLayout(null);
		animePnl.setVisible(true);
		
		showAnimePnl.setBorder(new TitledBorder(null, "Anime Name", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		showAnimePnl.setLayout(null);
		showAnimePnl.setVisible(true);
		
		int x=10;
        int y=40;
        for(String name : lblNames){
        	if(name=="Description :"){
        		lblContent = new JLabel(name);
                lblContent.setBounds(x,y,100,15);
                animePnl.add(lblContent);
                y += 50;
        	}else{
        	lblContent = new JLabel(name);
            lblContent.setBounds(x,y,100,15);
            animePnl.add(lblContent);
            y += 30;
        	}
        }
        
        
        lblID.setBounds(x+102+5,40,250,20);
        lblAnimeName.setBounds(x+102+5,70,250,20);
        lblDesc.setBounds(x+102+5,100,50,40);
        scrollPane.setBounds(x+102+5,100,250,40);
        lbldirector.setBounds(x+102+5,150,250,20);
        lblproducer.setBounds(x+102+5,180,250,20);
        lblrelease_date.setBounds(x+102+5,210,250,20);
        lblrt_score.setBounds(x+102+5,240,250,20);

        lblID.setEditable(false);
        lblAnimeName.setEditable(false);
        lblDesc.setEditable(false);

        animePnl.add(lbldirector);
        animePnl.add(lblproducer);
        animePnl.add(lblrelease_date);
        animePnl.add(lblrt_score);
        animePnl.add(scrollPane);
        animePnl.add(lblID);
        animePnl.add(lblAnimeName);
        animePnl.add(lblDesc);
        scrollPane.add(lblDesc);
		scrollPane.setViewportView(lblDesc);
		

        animeTbl.setBounds(10, 20, 260, 230);
        scrl.setBounds(10, 20, 260, 230);
        
        showAnimePnl.add(animeTbl);
		scrl.setViewportView(animeTbl);
        showAnimePnl.add(scrl);
        
		frame.add(lblTitle);
		frame.add(showAnimePnl);
		frame.add(animePnl);
        frame.setVisible(true);
        
        
        animeTbl.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				String selection = animeTbl.getValueAt(animeTbl.getSelectedRow(), 0).toString();

				for(int i = 0 ; i < jsnArr.length() ; i++){
					JSONObject jsnObj = jsnArr.optJSONObject(i);
					try{
						if(jsnObj.getString("id")==selection){
							String animeID = jsnObj.getString("id");
							String animeTitle= jsnObj.getString("title");
							String animeDesc= jsnObj.getString("description");
							String director= jsnObj.getString("director");
							String producer= jsnObj.getString("producer");
							String release_date= jsnObj.getString("release_date");
							String rt_score= jsnObj.getString("rt_score");

							lblID.setText(animeID);
							lblAnimeName.setText(animeTitle);
							lblDesc.setText(animeDesc);
							lbldirector.setText(director);
							lblproducer.setText(producer);
							lblrelease_date.setText(release_date);
							lblrt_score.setText(rt_score);
						}
					}catch(Exception e2){
						e2.printStackTrace();
					}
					}
			}
        });
        
        populateJTable();
    }
    
    public void populateJTable() {
		String[] columnName = {"ID","Movie Name"};
		String strUrl = "https://ghibliapi.herokuapp.com/films";
		JSONArray jsnArr = makeHTTPRequest(strUrl,"GET");
		JSONObject jsnObj = new JSONObject();
		Object[][] rows = new Object[jsnArr.length()][2];
		for(int index = 0 ; index < jsnArr.length() ; index++){
			jsnObj = jsnArr.optJSONObject(index);
			try{
			String animeID = jsnObj.getString("id");
			String animeTitle= jsnObj.getString("title");
			
			rows[index][0] = animeID;
			rows[index][1] = animeTitle;
			}catch(Exception e){
				
			}
		}
		// model
		animeModel model1 = new animeModel(rows,columnName);
		animeTbl.setModel(model1);
		animeTbl.setRowHeight(20);
	}
    
    public JSONArray makeHTTPRequest(String url,String method){
		InputStream is = null;
		String json = "";
		JSONObject jObj = null;
		
		
		try{
			if(method=="POST"){
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(url);
//				httpPost.setEntity(new UrlEncodedFormEntity(params));
				
				HttpResponse httpResponse = httpClient.execute(httpPost);
				HttpEntity httpEntity = httpResponse.getEntity();
				is = httpEntity.getContent();
			}else if (method == "GET"){
				DefaultHttpClient httpClient = new DefaultHttpClient();
//				String paramString = URLEncodedUtils.format(params, "utf-8");
//				url += "?"+paramString;
				
				HttpGet httpGet = new HttpGet(url);
				HttpResponse httpResponse = httpClient.execute(httpGet);
				HttpEntity httpEntity = httpResponse.getEntity();
				is = httpEntity.getContent();
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			
			while((line=reader.readLine())!=null){
				sb.append(line+"\n");
			}
			is.close();
			json = sb.toString();
			jObj = new JSONObject(json);
			jsnArr.put(jObj);

		}catch(JSONException e){
			try{
				jsnArr = new JSONArray(json);
			}catch(JSONException e1){
				e1.printStackTrace();
			}
		}catch(Exception ee){
			ee.printStackTrace();
		}
		
		return jsnArr;
	}
}