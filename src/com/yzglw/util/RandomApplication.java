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

	static private final String[] NAME_LIB = { "付莉珺", "吴凡", "丁晨溦", "郭玉晨", "侯佳琦",
			"李自然", "刘瑞元", "孙梦竹", "唐艺珍", "王梅", "王心语", "薛蕾", "姚锐", "余果", "张汝倩",
			"杜幼林", "高志", "孔德爽", "李渊", "粱西龙", "彭程", "彭志文", "屈顺城", "石相文", "孙佩",
			"王震", "伍至煊", "杨晓旭", "张翕然", "朱弘祖"
	};

	static private final String[] HIGH_LIB = { "山东省烟台市一中", "北京市朝阳区二中", "北京市海淀区一中",
			"北京市大兴区二中", "北京市平谷区三中", "湖南省永州市四中", "河南省周口市五中", "北京市大兴区六中",
			"四川省成都市七中", "四川省绵阳市八中", "山东省济南市一中", "北京市通州区二中", "河南省南阳市三中",
			"湖南省岳阳市四中", "安徽省毫州市五中", "河北省唐山市六中", "山东省泰安市七中", "北京市房山区八中",
			"四川省巴中市一中", "山东省日照市二中", "山东省临沂市三中", "湖南省常德市四中", "北京市平谷区五中",
			"河南省焦作市六中", "山西省大同市七中", "河南省邓州市八中", "湖南省怀化市一中", "北京市宣武区二中",
			"四川省南充市三中", "北京市朝阳区四中"
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
		{ "金融学", "环境工程", "市场营销", "经济学", "项目及工程管理", "法学", "考古研究", "应用犯罪学", "合唱团研究" }, 
		{ "计算机科学与技术", "经济和社会史", "房地产金融", "土地经济", "近代早期历史", "血液科", "干细胞研究" },
		{ "通信工程", "材料科学与冶金", "可持续发展工程", "极地研究", "晶体数据中心", "认知与脑科学" }
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
