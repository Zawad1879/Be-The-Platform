package me.argha.tonu.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.argha.tonu.adapter.ExpandableListAdapter;
import me.argha.tonu.R;

public class FAQActivity extends AppCompatActivity {
    private static ExpandableListView expandableListView;
    private static List<String> listOfHeaders, listOfDetails;
    private static HashMap<String,String> map;
    private static ExpandableListAdapter expandableListAdapter;
    public static final String child="CHILD", header="HEADER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information_layout);
        expandableListView = (ExpandableListView) findViewById(R.id.expandable_list_view_faq);

        // preparing list data
        prepareListData();
        expandableListAdapter = new ExpandableListAdapter(this, listOfHeaders, map);

        // setting list adapter
        expandableListView.setAdapter(expandableListAdapter);

    }

    public void prepareListData(){
        listOfHeaders = new ArrayList<String>();
        listOfDetails = new ArrayList<String>();
        map= new HashMap<>();
        listOfHeaders.add("1. What is women violation?\n");
        listOfHeaders.add("2. What are the human right reserved for Women?");
        listOfHeaders.add("3. What is true liberation for both sexes?");
        listOfHeaders.add("4.What is Domestic violence?");
        listOfHeaders.add("5..How to stop violation?");
        listOfDetails.add("ANS:\nViolence against Women is a Violation of Human Rights and fundamental freedoms of women. Violence against women is an obstacle towards gender equality.");
        listOfDetails.add("ANS:\n1.The right to life;\n" +
                "2.The right to equality;\n" +
                "3.The right to liberty and security of person;\n" +
                "4.The right to equal protection under the law;\n" +
                "5.The right to be free from all forms of discrimination;\n" +
                "6.The right to the highest attainable standard of physical a and mental health;\n" +
                "7.The right to just and favourable conditions of work;\n" +
                "8.The right not to be subjected to torture, or other cruel, inhuman or degrading" +
                " treatment or punishment.");
        listOfDetails.add("ANS:\nThough feminism rightfully calls for the end of male domination and abuse, and for equal rights for women, it is vital to get to the root of the distortion — that our focus in life, as man or woman, must not be simply to satisfy our own ego or needs, but to serve G-d. True women’s liberation does not mean merely seeking equality within a masculine world, but liberating the divine feminine aspects of a woman’s personality and using them for the benefit of humankind.\n" +
                "After so many years of male dominance, we are standing at the threshold of a true feminine era. It is time now for the woman to rise to her true prominence, when the subtle power of the feminine energy is truly allowed to nourish the overt power of the masculine energy. We have already proven that we can use our strength to slay the demons around us; let us now learn to nurture the G-dliness within.");
        listOfDetails.add("ANS:\nViolence against women is a global pandemic. Without exception, a woman's greatest risk of violence is from someone she knows. Domestic violence is a violation of a woman's right to physical integrity, to liberty, and all too often, to her right to life itself. When states fail to take the basic steps needed to protect women from domestic violence or allow these crimes to be committed with impunity, states are failing in their obligation to protect women from torture.");
        listOfDetails.add("ANS:\n1. “No” means No. Not “maybe.” Not “I’m just playing hard to get.” Not “later.” Not anything but NO. This isn’t rocket science so I’m not sure why so many men aren’t getting the message. Start listening to what women are saying not what you want them to be saying. And, if a woman is too drunk, stoned, drugged, or medicated, that means NO too. And, having sex with a woman in that condition is RAPE and a CRIME.\n" +
                "\n" +
                "2. Stop supporting movies and television that depict women in subordinate roles and as sex objects. Avoid movies and television that glorify rape and other sexual abuses.\n" +
                "\n" +
                "3. Don’t support porn. Not only is the porn industry engaged in violence against women, research in Psychology Today found that people who watch porn are: 1) more likely to be desensitized to violence against women; 2) more likely to blame the victim of rape and violent crimes against women; and 3) more likely to rape a woman.\n" +
                "\n" +
                "4. Avoid strip clubs and don’t accept a partner who supports these places. I was reading an article recently that indicated many female strippers are threatened with or subjected to violence and are also often the victims of human trafficking.\n" +
                "\n" +
                "5. Stop demeaning the feminine by saying things like “you run like a girl,” “you throw like a girl,” or “he cried like a little girl.” That includes referring to men or boys as “girls” when you are meaning something derogatory. Don’t refer to a woman as a “bitch,” “ho” or “whore.”\n" +
                "6. Speak up against violence against women. Years ago I overheard a guy I went to school with say to his buddies, “I’d love to rape her” and motion in the direction of a waitress. No one said anything to him. There is no excuse for violence against women or even language that condones or promotes it.\n" +
                "\n" +
                "7. If you know a victim of abuse get the police involved. This woman’s life may be at risk.\n" +
                "\n" +
                "8. Be a hero. Reach out to show your support and assistance to women who are victims of violence.\n" +
                "\n" +
                "9. Speak up against sexism, sexist comments and sexist jokes. Devaluing women or valuing them for sex is at the root of violent crimes against women. Sexist comments and jokes devalue women.\n" +
                "\n" +
                "10. Learn the law and use it. Ask for police support. Press charges against a man who is violent toward you or one who threatens violence against you.\n" +
                "\n" +
                "11. Don’t buy from sexist companies and don’t support sexist organizations. They’re only profiting from sexism or from promoting women as sex objects. A colleague told me her partner bought a “Wife Beater” T-shirt.  Astounded, I asked her why she accepted that her partner was sexist and promoting violence against women, not to mention demeaning a serious crime that causes thousands of women’s deaths every year.\n" +
                "12. Donate to legitimate organizations and charities that advocate for women, support them when they’ve been victimized by violence, or help them overcome difficult circumstances.\n" +
                "\n" +
                "13. Teach your daughters and sons about fair and respectful treatment of girls and women, including rape myths (the disturbing notion that a woman actually wants to be raped or somehow deserves to be raped). Teach them that date rape is a serious crime, making any man who pushes or forces a woman to have sex a rapist and a criminal.\n" +
                "\n" +
                "14. Don’t engage in revenge porn—posting naked pictures of a partner on websites after she breaks up with you. And, women, if he loves you he doesn’t need to exploit you through naked photos.\n" +
                "\n" +
                "15. Recognize that the way a woman is dressed or not dressed has nothing to do with whether she “deserved it” or “had it coming.” No matter how a woman is dressed, it is not an invitation to any man to mistreat her.\n" +
                "\n" +
                "16. Expect more from the men in your life. Men: expect more from yourself. Don’t accept or engage in language or actions that condone or promote sexism or violence against women.\n" +
                "\n" +
                "17. Think before you speak. Next time you say something about “girls” or “women” imagine how the same expression would sound if you substituted the name of an ethnic, cultural or religious group. If it sounds racist or prejudice then you know it will be sexist and equally unacceptable if you say the same comment against women or girls.\n" +
                "18. Stop prematurely sexualizing young girls. Parents don’t let your kids dress in micro-miniskirts, high heels, and makeup. I was in a café where a girl about 9 or 10 kept falling on the ground. It was obvious that her high heels were the issue. She can make these choices for herself when she’s older.\n" +
                "\n" +
                "19. If you’re involved in an abusive relationship, reach out to a shelter or women’s group for help. You don’t need to remain isolated. Most abusive men attempt to keep their abused partners isolated and controlled. If you’re subjected to verbal abuse tell yourself you deserve better (you do!) and get out. With 7 billion people on the planet, you really can do better than someone who abuses you.\n" +
                "\n" +
                "20. Share articles and information about ending sexism and violence against women through social media like Facebook and Twitter. Raising awareness helps overcome the problem.\n" +
                "\n" +
                "21. Write to your political representatives making them accountable for sexist legislation, including insufficient sentences for men who commit crimes against women.\n" +
                "\n" +
                "22. Take a self defense or martial arts course. When I was 17, my mom asked me to take a self defense course with her. I not only came out feeling more capable of protecting myself, I felt empowered. Most men who commit crimes against women are cowards who frequently back down when they suspect they’ll be challenged.");

        int i=0;
        for(String h1:listOfHeaders){
            map.put(h1,listOfDetails.get(i));
            i++;
        }

    }

}
