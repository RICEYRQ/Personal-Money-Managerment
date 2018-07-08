package com.example.riceyrq.personalmoneymanagerment.acitvity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.riceyrq.personalmoneymanagerment.R;
import com.example.riceyrq.personalmoneymanagerment.acitvity.fragment.AboutFragment;
import com.example.riceyrq.personalmoneymanagerment.acitvity.fragment.AddFragment;
import com.example.riceyrq.personalmoneymanagerment.acitvity.fragment.MonthPicFragment;
import com.example.riceyrq.personalmoneymanagerment.acitvity.fragment.ShowFragment;
import com.example.riceyrq.personalmoneymanagerment.acitvity.fragment.YearPicFragment;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class Main extends Activity implements Drawer.OnDrawerItemClickListener{

    public Drawer drawer;
    private final long ABOUT = 1;
    private final long CANCEL = 2;
    private final long SHOW = 3;
    private final long ADD = 4;
    private final long MONTHPIC = 5;
    private final long YEARPIC = 6;
    private final long RESTORE = 7;
    private final long BACKUP = 8;
    private String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){//4.4 全透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        username = getIntent().getStringExtra("username");

        drawer = new DrawerBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(true)
                .withHeader(R.layout.drawer_header)
                .addDrawerItems(
                        new PrimaryDrawerItem()
                            .withName(R.string.show)
                            .withIdentifier(SHOW),
                        new PrimaryDrawerItem()
                            .withName(R.string.add)
                            .withIdentifier(ADD),
                        new PrimaryDrawerItem()
                            .withName(R.string.monthPic)
                            .withIdentifier(MONTHPIC),
                        new PrimaryDrawerItem()
                            .withName(R.string.yearPic)
                            .withIdentifier(YEARPIC)
                )
                .addStickyDrawerItems(
                        new PrimaryDrawerItem()
                            .withName(R.string.backup)
                            .withIdentifier(BACKUP),
                        new PrimaryDrawerItem()
                            .withName(R.string.restore)
                            .withIdentifier(RESTORE),
                        new PrimaryDrawerItem()
                            .withName(R.string.about)
                            .withIdentifier(ABOUT),
                        new PrimaryDrawerItem()
                            .withName(R.string.cancel)
                            .withIdentifier(CANCEL)
                )
                .withOnDrawerItemClickListener(this)
                .withActionBarDrawerToggle(true)
                .withSavedInstance(savedInstanceState)
                .build();

        if (savedInstanceState == null){
            displayFragment(new ShowFragment());
        }





    }

    public void displayFragment(Fragment fragment){
        getFragmentManager().beginTransaction().replace(R.id.fragment_holder, fragment).commitAllowingStateLoss();
        drawer.closeDrawer();
    }

    public String getUsername(){
        return username;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        drawer.saveInstanceState(outState);

    }

    @Override
    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
        if (drawerItem.getIdentifier() == ABOUT) {
            displayFragment(new AboutFragment());
        } else if (drawerItem.getIdentifier() == CANCEL) {
            Intent intent = new Intent();
            intent.setClass(Main.this, Login.class);
            startActivity(intent);
            Main.this.finish();
        } else if (drawerItem.getIdentifier() == SHOW) {
            displayFragment(new ShowFragment());
        } else if (drawerItem.getIdentifier() == ADD) {
            displayFragment(new AddFragment());
        } else if (drawerItem.getIdentifier() == MONTHPIC) {
            displayFragment(new MonthPicFragment());
        } else if (drawerItem.getIdentifier() == YEARPIC) {
            displayFragment(new YearPicFragment());
        } else if (drawerItem.getIdentifier() == BACKUP) {

        } else if (drawerItem.getIdentifier() == RESTORE) {

        }

        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
