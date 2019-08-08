package com.server.servercomtest.Repository;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.server.servercomtest.Data.UserData;
import com.server.servercomtest.Interface.RepositoryService;

public class FireStoreRepository implements RepositoryService {
    FirebaseStorage st;
    public FireStoreRepository(){
        st = FirebaseStorage.getInstance();
    }
    @Override
    public UserData getdata() {
        return null;
    }

    @Override
    public void AddData(UserData sd) {

    }

    @Override
    public void SetData(UserData sdl) {

    }
    public void ProfliePictureSet(Context ctx,String eamil,ImageView v){
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference islandRef = storageRef.child("profilepicture/"+eamil+"/" + "test.jpg");
        Glide.with(ctx).load(islandRef).apply(RequestOptions.circleCropTransform()).into(v);
    }
    //이미지 스토라지에 업로드
    public void pictureupload(String email, String imagename, Uri uip) {
        // Create a storage reference from our app

        StorageReference storageRef = st.getReference();

        //경로없이 버킷 제일밖에 지정
        StorageReference mountainsRef = storageRef.child(imagename);
        //경로까지 지정
        StorageReference mountainImagesRef = storageRef.child("profilepicture/" + email + "/" + imagename);

        Uri file = uip;
        StorageReference riversRef = storageRef.child("profilepicture/" + email + "/" + imagename);


//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        byte[] data = baos.toByteArray();
//        UploadTask uploadTask = mountainImagesRef.putBytes(data);
        UploadTask uploadTask = riversRef.putFile(file);

// Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Log.d("실패", "업로드실패");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                Log.d("성공", "업로드성공");
            }
        });
    }
}
