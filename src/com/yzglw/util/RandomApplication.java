package com.yzglw.util;

import java.util.Random;

import com.yzglw.lx.PhysicalApplication;
import com.yzglw.lx.PhysicalUniversity;
import com.yzglw.lx.ReportCard;

/**
 * @author OSC
 *
 */
public class RandomApplication {

	static private Random seed = new Random();

	static private final String[] NAME_LIB = { "����B", "�ⷲ", "������", "����", "�����",
			"����Ȼ", "����Ԫ", "������", "������", "��÷", "������", "Ѧ��", "Ҧ��", "���", "����ٻ",
			"������", "��־", "�׵�ˬ", "��Ԩ", "������", "���", "��־��", "��˳��", "ʯ����", "����",
			"����", "������", "������", "����Ȼ", "�����"
	};

	static private final String[] HIGH_LIB = { "ɽ��ʡ��̨��һ��", "�����г���������", "�����к�����һ��",
			"�����д���������", "������ƽ��������", "����ʡ����������", "����ʡ�ܿ�������", "�����д���������",
			"�Ĵ�ʡ�ɶ�������", "�Ĵ�ʡ�����а���", "ɽ��ʡ������һ��", "������ͨ��������", "����ʡ����������",
			"����ʡ����������", "����ʡ����������", "�ӱ�ʡ��ɽ������", "ɽ��ʡ̩��������", "�����з�ɽ������",
			"�Ĵ�ʡ������һ��", "ɽ��ʡ�����ж���", "ɽ��ʡ����������", "����ʡ����������", "������ƽ��������",
			"����ʡ����������", "ɽ��ʡ��ͬ������", "����ʡ�����а���", "����ʡ������һ��", "����������������",
			"�Ĵ�ʡ�ϳ�������", "�����г���������"
	};

	static private final String[] EMAIL_LIB = { "632822487@qq.com",
			"1062594916@qq.com", "824058224@qq.com", "wanxgfg@yahoo.cn",
			"a18801170148@163.com", "351724647@qq.com", "676550381@qq.com",
			"862795455@qq.com", "919067644@qq.com", "845047522@qq.com",
			"lucykatty22@163.com", "18701331704@163.com", "281958631@qq.com",
			"739467685@qq.com", "2290018141@qq.com", "1158806819@qq.com",
			"729834508@qq.com", "392964921@qq.com", "353037236@qq.com",
			"10784818@qq.com", "1164809718@qq.com", "994687913@qq.com",
			"846383892@qq.com", "1024056887@qq.com", "448653795@qq.com",
			"313997417@qq.com", "wzx181@gmail.com", "653842516@qq.com",
			"zxrqqabcde@qq.com", "1045382056@qq.com"
	};

	static public final String[] UNI_LIB = { PhysicalUniversity.AUU,
			PhysicalUniversity.TUS, PhysicalUniversity.TUN
	};

	static public final String[][] MAJ_LIB = { 
		{ "����ѧ", "��������", "�г�Ӫ��", "����ѧ", "��Ŀ�����̹���", "��ѧ", "�����о�", "Ӧ�÷���ѧ", "�ϳ����о�" }, 
		{ "�������ѧ�뼼��", "���ú����ʷ", "���ز�����", "���ؾ���", "����������ʷ", "ѪҺ��", "��ϸ���о�" },
		{ "ͨ�Ź���", "���Ͽ�ѧ��ұ��", "�ɳ�����չ����", "�����о�", "������������", "��֪���Կ�ѧ" }
	};

	public static PhysicalApplication getInstance() {
		PhysicalApplication app = null;
		ReportCard card = null;

		card = getInstanceCard();
		int uniIndex = seed.nextInt(UNI_LIB.length);
		String tarUni = UNI_LIB[uniIndex];
		String tarMaj = MAJ_LIB[uniIndex][seed.nextInt(MAJ_LIB[uniIndex].length)];
		
		if (seed.nextInt(10) >= 3) {
			int anotherIndex = seed.nextInt(UNI_LIB.length);
			tarMaj = MAJ_LIB[anotherIndex][seed.nextInt(MAJ_LIB[anotherIndex].length)];
		}
		app = new PhysicalApplication(card, tarUni, tarMaj);

		return app;
	}

	private static ReportCard getInstanceCard() {
		ReportCard card = null;
		String[] eduInfo = new String[4];

		eduInfo[0] = NAME_LIB[seed.nextInt(NAME_LIB.length)];
		eduInfo[1] = HIGH_LIB[seed.nextInt(HIGH_LIB.length)];
		eduInfo[2] = EMAIL_LIB[seed.nextInt(EMAIL_LIB.length)];
		eduInfo[3] = "";

		int chn = ReportCard.INIT_GRADE_ARRAY[0] + seed.nextInt(30);
		int mth = ReportCard.INIT_GRADE_ARRAY[1] + seed.nextInt(30);
		int eng = ReportCard.INIT_GRADE_ARRAY[2] + seed.nextInt(30);
		int sci = ReportCard.INIT_GRADE_ARRAY[3] + seed.nextInt(60);

		int[] gradeArray = { chn, mth, eng, sci };
		card = new ReportCard(gradeArray, eduInfo);
		
		if (seed.nextInt(10) < 8) {
			card.check();
		}

		return card;
	}
	
	public static void main(String[] args) {
		PhysicalApplication app = RandomApplication.getInstance();
		String idStr = "1";
		int i = 4;
		while (i-- > 0) {
			idStr += Integer.toString(seed.nextInt(10));
		}
		app.setApplicationId(Integer.parseInt(idStr));
		app.getReportCard().setResume("Nothing");
		System.out.println(app.toString());
	}
}
