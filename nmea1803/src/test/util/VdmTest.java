package test.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.antu.nmea.codec.AcceptanceList;
import com.antu.nmea.codec.CodecManager;
import com.antu.nmea.codec.exception.SentenceCodecNotFoundException;
import com.antu.nmea.codec.exception.SentenceFieldCodecNotFoundException;
import com.antu.nmea.sentence.MutiVdmSentence;
import com.antu.nmea.sentence.VdmSentence;
import com.antu.nmea.util.SentenceStore;
public class VdmTest {
	public List<MutiVdmSentence> decodeTest() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SentenceFieldCodecNotFoundException, SentenceCodecNotFoundException{
		List<MutiVdmSentence> listforsentence = new ArrayList<MutiVdmSentence> ();
		SentenceStore<Integer,MutiVdmSentence> store =new SentenceStore<Integer,MutiVdmSentence>();
		String[] list= new String[3];
		list[0]="!AIVDM,2,1,1,B,16:>>s5Oh08dLO8AsMAVqptj0@>p,0*67";
		list[1]="!AIVDM,2,1,2,B,16:>>s5Oh08dLO8AsMAVqptj0@>p,0*67";
		list[2]="!AIVDM,2,2,1,B,16:>>s5Oh08dLO8AsMAVqptj0@>p,0*67";
		for(String temp:list){
			String content1 = temp.substring(temp.indexOf("!")+1, temp.indexOf("*"));
			String[] contents = content1.split(",");
			VdmSentence vdm =new VdmSentence();
			vdm.setTotalSentenceCount(Integer.valueOf(contents[1]));
			vdm.setSequenceId(Integer.valueOf(contents[2]));
			vdm.setSentenceNumber(Integer.valueOf(contents[3]));
			vdm.setAisChannel(contents[4].toCharArray()[0]);
			vdm.setEncapsulatedData(contents[5]);
			vdm.setFillBits(Integer.valueOf(contents[6]));
			MutiVdmSentence tempform = store.addItem(Integer.valueOf(contents[2]),new MutiVdmSentence(new Date(),vdm));
			if(tempform!=null){
				//½øÐÐ½âÎö
				listforsentence.add(tempform);
			}
		}
		return listforsentence;
	}
	public static void main(String[] args) {
		
		try {
			VdmTest test = new VdmTest();
			List<MutiVdmSentence> temp =test.decodeTest();
			CodecManager manager = new CodecManager();
			for(MutiVdmSentence m :temp){
				manager.decode(m.getVdm().getEncapsulatedData());
			}
			AcceptanceList list =manager.acceptanceList();
			System.out.println(list.toString());
		
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SentenceFieldCodecNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SentenceCodecNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
