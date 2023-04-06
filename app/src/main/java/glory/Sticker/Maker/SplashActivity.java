package glory.Sticker.Maker;


import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pesonal.adsdk.ADS_SplashActivity;
import com.pesonal.adsdk.getDataListner;

import org.json.JSONObject;

public class SplashActivity extends ADS_SplashActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slpash);


        Intent i = new Intent(getApplicationContext(), EntryActivity.class);
        startActivity(i);
        finish();

        ADSinit(this, getCurrentVersionCode(), new getDataListner() {
            @Override
            public void onSuccess() {
                Intent i = new Intent(getApplicationContext(), EntryActivity.class);
                startActivity(i);
                finish();

            }

            @Override
            public void onUpdate(String url) {
                Log.e("my_log", "onUpdate: $url");
                showUpdateDialog(url);
            }

            @Override
            public void onRedirect(String url) {
                Log.d("my_log", "onRedirect: $url");
                showRedirectDialog(url);
            }

            @Override
            public void onReload() {
                startActivity(new Intent(SplashActivity.this, EntryActivity.class));
                finish();
            }

            @Override
            public void onGetExtradata(JSONObject extraData) {
                Log.d("my_log", "ongetExtradata: " + extraData.toString());
            }
        });
    }

    public void showRedirectDialog(final String url) {
        Dialog dialog = new Dialog(SplashActivity.this);
        dialog.setCancelable(false);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(com.pesonal.adsdk.R.layout.installnewappdialog, null);
        dialog.setContentView(view);
        TextView update = view.findViewById(com.pesonal.adsdk.R.id.update);
        TextView title = view.findViewById(R.id.txt_title);
        TextView decription = view.findViewById(R.id.txt_decription);
        update.setText("Install Now");
        title.setText("Install our new app now and enjoy");
        decription.setText("We have transferred our server, so install our new app by clicking the button below to enjoy the new features of app.");
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri marketUri = Uri.parse(url);
                Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
                startActivity(marketIntent);
            }
        });
        dialog.create();
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
    public void showUpdateDialog(final String url) {
        Dialog dialog = new Dialog(SplashActivity.this);
        dialog.setCancelable(false);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(com.pesonal.adsdk.R.layout.installnewappdialog, null);
        dialog.setContentView(view);
        TextView update = view.findViewById(R.id.update);
        TextView title = view.findViewById(R.id.txt_title);
        TextView decription = view.findViewById(R.id.txt_decription);
        update.setText("Update Now");
        title.setText("Update our new app now and enjoy");
        decription.setText("");
        decription.setVisibility(View.GONE);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Uri marketUri = Uri.parse(url);
                    Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
                    startActivity(marketIntent);
                } catch (ActivityNotFoundException ignored1) {
                }
            }
        });
        {

        }
        dialog.create();
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    };

    public int getCurrentVersionCode() {
        PackageManager manager = getPackageManager();
        PackageInfo info;
        try {
            info = manager.getPackageInfo(getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    ;

}