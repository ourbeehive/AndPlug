package com.lean56.andplug.app.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;
import com.lean56.andplug.activity.BaseActivity;
import com.lean56.andplug.app.AppContext;
import com.lean56.andplug.app.R;
import com.lean56.andplug.view.ResetEditText;

/**
 * A login_bg screen that offers login_bg via username/password.
 *
 * @author Charles
 */
public class LoginActivity extends BaseActivity {

    /**
     * Keep track of the login_bg task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private ResetEditText mUsernameEdit;
    private ResetEditText mPwdEdit;
    private Button mLoginBtn;
    private TextView mForgetPwdText;
    private TextView mRegisterText;

    @Override
    protected int getContentView() {
        return R.layout.login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set up the login form.
        mUsernameEdit = (ResetEditText) findViewById(R.id.et_username);
        mUsernameEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    mPwdEdit.requestFocus();
                }
                return true;
            }
        });

        mPwdEdit = (ResetEditText) findViewById(R.id.et_pwd);
        mPwdEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mLoginBtn = (Button) findViewById(R.id.btn_login);
        mLoginBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
    }

    @Override
    protected void addMenuItem(Menu menu) {
        // add menu like this
        menu.add(Menu.NONE, Menu.FIRST, Menu.NONE, R.string.register).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    protected boolean onFirstMenuSelected(MenuItem item) {
        AppContext.showToast("zhuce");
        return true;
    }

    /**
     * Authenticate login & password
     */
    public void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Store values at the time of the login_bg attempt.
        String username = mUsernameEdit.getText().toString();
        String password = mPwdEdit.getText().toString();

        boolean cancel = false;
        ResetEditText focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPwdEdit.setError(getString(R.string.error_invalid_password));
            focusView = mPwdEdit;
            cancel = true;
        }

        // Check for a valid username.
        if (TextUtils.isEmpty(username)) {
            mUsernameEdit.setError(getString(R.string.error_field_required));
            focusView = mUsernameEdit;
            cancel = true;
        } else if (!isUsernameValid(username)) {
            mUsernameEdit.setError(getString(R.string.error_invalid_username));
            focusView = mUsernameEdit;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login_bg and focus the first
            // form field with an error.
            focusView.requestFocus();
            focusView.shakeAnimation();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login_bg attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(username, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isUsernameValid(String username) {
        return username.length() > 0;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 0;
    }

    /**
     * Shows the progress UI and hides the login_bg form.
     */
    public void showProgress(final boolean show) {
        /*int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });*/
    }

    /**
     * Represents an asynchronous login_bg/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            /*try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }*/

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
                mPwdEdit.setError(getString(R.string.error_invalid_password));
                mPwdEdit.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}



