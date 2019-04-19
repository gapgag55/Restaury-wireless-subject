package com.mang.restaury.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mang.restaury.Model.User;
import com.mang.restaury.R;

import java.util.Arrays;

public class AuthenticationFragment extends BottomSheetDialogFragment {

    // for google sign in
    private static final String TAG = "GoogleActivity";
    private static final int GOOGLE_SIGN_IN_REQUEST_CODE = 0;

    // for facebook sign in
    private final int FACEBOOK_LOG_IN_REQUEST_CODE = 64206;


    private FirebaseAuth mAuth;


    private GoogleSignInClient mGoogleSignInClient;
    private CallbackManager callbackManager;

    Button loginGoogle;
    Button loginFacebook;
    LoginButton loginFacebookHide;

    public static AuthenticationFragment getInstance() {
        return new AuthenticationFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.bottom_sheet_login, container, false);

        loginGoogle = (Button) view.findViewById(R.id.login_google);
        loginFacebook = (Button) view.findViewById(R.id.login_facebook);

        loginFacebookHide = (LoginButton) view.findViewById(R.id.login_facebook_hide);
        loginFacebookHide.setVisibility(View.GONE);


        initFBGoogleSignIn();
        initFBFacebookLogIn();

        loginGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInWithGoogleSignIn();
            }
        });

        loginFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginFacebookHide.performClick();
            }
        });

        mAuth = FirebaseAuth.getInstance();

        return view;
    }

    public FirebaseUser getCurrentUser() {
        mAuth = FirebaseAuth.getInstance();
        return mAuth.getCurrentUser() != null ? mAuth.getCurrentUser() : null;
    }

    private void initFBGoogleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);
    }

    // [START auth_with_facebook]
    private void initFBFacebookLogIn() {
        callbackManager = CallbackManager.Factory.create();
        loginFacebookHide.setFragment(this);
        loginFacebookHide.setReadPermissions("email", "public_profile");
        loginFacebookHide.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                System.out.println("facebook:onSuccess:" + loginResult);
                firebasAuthWithFacebook(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                System.out.println("facebook:cancel:");
            }

            @Override
            public void onError(FacebookException error) {
                System.out.println("facebook:onError: " + error);
            }
        });
    }

    private void firebasAuthWithFacebook(AccessToken token) {

        this.dismiss();

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(AuthenticationFragment.this.getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            final String uid = mAuth.getCurrentUser().getUid();
//
                            String[] splitFullName = mAuth.getCurrentUser().getDisplayName().toString().split(" ");
                            final String firstName = splitFullName[0];
                            final String lastName = splitFullName[1];
                            final String email = mAuth.getCurrentUser().getEmail();

                            final FirebaseDatabase database = FirebaseDatabase.getInstance();
                            final DatabaseReference ref = database.getReference();

                            Query userRef = ref.child("User").child(uid);
                            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                               @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                   if (!dataSnapshot.exists()) {
                                       ref.child("User").child(uid).setValue(
                                               new User(firstName, lastName, "", "", email)
                                       );
                                   }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                }
                            });


                        } else {

                        }
                    }
                });
    }
    // [END auth_with_facebook]

    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {

        this.dismiss();

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            final String uid = mAuth.getCurrentUser().getUid();

                            final FirebaseDatabase database = FirebaseDatabase.getInstance();
                            final DatabaseReference ref = database.getReference();

                            Query userRef = ref.child("User").child(uid);
                            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (!dataSnapshot.exists()) {
                                        ref.child("User").child(uid).setValue(
                                                new User(acct.getGivenName(), acct.getFamilyName(), "", "", acct.getEmail())
                                        );
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                }
                            });


                        } else {

                        }
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == GOOGLE_SIGN_IN_REQUEST_CODE) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        } else if (requestCode == FACEBOOK_LOG_IN_REQUEST_CODE) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void signInWithGoogleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN_REQUEST_CODE);
    }

    public void signOut() {
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
    }

}