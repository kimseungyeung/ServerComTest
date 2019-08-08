package com.server.servercomtest.Network;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.server.servercomtest.Activity.LoginActivity;
import com.server.servercomtest.Activity.MainActivity;
import com.server.servercomtest.Constants;
import com.server.servercomtest.Data.UserData;
import com.server.servercomtest.Interface.NetworkService;
import com.server.servercomtest.R;

import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class FireStoreNetwork implements NetworkService {
    Context con=null;
    String TAG = "acountset";
    private FirebaseFirestore db=null;
    FirebaseAuth mAuth=null;

    public FireStoreNetwork(Context ctx) {
        this.con = ctx;
        FirebaseApp.initializeApp(con);
        this.mAuth = FirebaseAuth.getInstance();
        this.db = FirebaseFirestore.getInstance();
    }

    @Override
    public String connect(String username, String password) {
        return null;
    }

    @Override
    public String signup(String token, JSONObject json) {
        return null;
    }

    @Override
    public void signup(final UserData ud) {
        mAuth.createUserWithEmailAndPassword(ud.getUsername(),ud.getPassword())
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.d("성공", "계정생성성공");
                         //   toastmessage(getApplicationContext(), getString(R.string.join_sucess));

                            String uid = task.getResult().getUser().getUid();
                            add_user_data(ud,uid);
                          //  dialog.cancel();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(con, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            Log.d("실패", "계정생성실패");
                           // toastmessage(getApplicationContext(), getString(R.string.join_fail));
                            if (task.getException().getMessage().toString().contains("The email address is " +
                                    "already in use by another account")) {
                              //  toastmessage(getApplicationContext(), getString(R.string.email_duplicate));
                            }
                        }

                        // ...
                    }
                });
    }



    @Override
    public void signin(final String username, String password) {
        mAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    Log.d("성공", "로그인성공");
                    //  toastmessage(getApplicationContext(), getString(R.string.login_sucess));


//                    String token = FirebaseInstanceId.getInstance().getToken();
//
//                    DocumentReference docRef = db.collection("usertoken").document(username);
//                    docRef.update("devicetoken", token).addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            Log.e("성공", "토큰업데이트 성공");
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Log.e("실패", e.getMessage().toString());
//                        }
//                    });

                    Intent intent = new Intent(con,new MainActivity().getClass());
                    intent.putExtra("uid",mAuth.getCurrentUser().getUid());
                   // Constants.loginfn=FireStoreNetwork.this;
                    con.startActivity(intent);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                    Toast.makeText(con, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                    Log.d("실패", "로그인실패" + task.getException());

                }

                // ...
            }

        });


}
    @Override
    public String gettoken(String id, String password) {
        String token =FirebaseInstanceId.getInstance().getToken();
        return token;
    }

    @Override
    public String authorize(String sessionId) throws IOException {
        return null;
    }

    @Override
    public String token(String sessionId, String code) throws IOException {
        return null;
    }
    public void add_user_data(UserData userData,String uid) {
        String token = FirebaseInstanceId.getInstance().getToken();
        Map<String, Object> user = new HashMap<>();
        user.put("username", userData.getUsername());
        user.put("realname", userData.getRealname());
        user.put("email", userData.getEmail());
        user.put("phone",userData.getMobile());

        db.collection("userlist").document(uid).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("성공", "계정데이터추가 성공");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("실패", "계정데이터추가 실패");
            }
        });
        Map<String, Object> usertoken = new HashMap<>();
        usertoken.put("devicetoken", token);
        usertoken.put("uid", uid);
        db.collection("usertoken").document(userData.getEmail()).set(usertoken).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("성공", "토큰데이터추가 성공");
                mAuth.signOut();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("실패", "토큰데이터추가 실패");
            }
        });
    }

    public FirebaseAuth getmAuth() {
        return mAuth;
    }

    public FirebaseFirestore getDb() {
        return db;
    }

}
