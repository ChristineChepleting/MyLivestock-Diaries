package com.example.mylivestockdiaries.activities;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;
import com.example.mylivestockdiaries.R;
import com.example.mylivestockdiaries.fragments.FragmentAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class Finance extends AppCompatActivity {
    private Toolbar toolbar;
    public FloatingActionButton fab1,fab2,fab3;
    Animation fabOpen,fabClose,rotateForward,rotateBackward;
    boolean isOpen=false;
    TabLayout tabLayout;
    ViewPager2 pager2;
    FragmentAdapter fragmentadapter;
    boolean isFABOpen=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance);
        toolbar = findViewById(R.id.FinanceToolbar);
       fab1=findViewById(R.id.floatingActionButtonadd);
       fab2=findViewById(R.id.floatingActionButtonattachmoney);
       fab3=findViewById(R.id.floatingActionButtondetachmoney);
       fabOpen= AnimationUtils.loadAnimation(this,R.anim.fab_open);
       fabClose=AnimationUtils.loadAnimation(this,R.anim.fab_close);
       rotateForward=AnimationUtils.loadAnimation(this,R.anim.rotate_forward);
       rotateBackward=AnimationUtils.loadAnimation(this,R.anim.rotate_backward);


   fab1.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
       animateFab();

       }
   });


        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Transactions");
        tabLayout=findViewById(R.id.tablayout);
        pager2=findViewById(R.id.view_pager2);
        FragmentManager fm=getSupportFragmentManager();
        fragmentadapter=new FragmentAdapter(fm, getLifecycle());
        pager2.setAdapter(fragmentadapter);
        tabLayout.addTab(tabLayout.newTab().setText("INCOME"));
        tabLayout.addTab(tabLayout.newTab().setText("EXPENSES"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

    fab2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            animateFab();
            Toast.makeText(Finance.this, "Add income", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(Finance.this,IncomeActivity.class);
            intent.putExtra("key","null");
            intent.putExtra("title","Add income");
            startActivity(intent);
        }
    });

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               animateFab();
                Toast.makeText(Finance.this, "Add Expense", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Finance.this,ExpenseActivity.class);
                intent.putExtra("key","null");
                intent.putExtra("title","Add Expense");
                startActivity(intent);
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
    }

    private void animateFab(){
        if(isOpen){
            fab1.startAnimation(rotateForward);
            fab2.startAnimation(fabClose);
            fab3.startAnimation(fabClose);
            fab2.setClickable(false);
            fab3.setClickable(false);
            isOpen=false;
            }
            else{
            fab1.startAnimation(rotateBackward);
            fab2.startAnimation(fabOpen);
            fab3.startAnimation(fabOpen);
            fab2.setClickable(true);
            fab3.setClickable(true);
            isOpen=true;
             }


        }



    }
