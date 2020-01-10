package fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.insta.Insta.LoginForm.LoginFragment;
import com.example.insta.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {
    private Button btnLogout;

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_account, container, false);
        btnLogout = view.findViewById(R.id.btnLogout);
     btnLogout.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

             Toast.makeText(getContext(),"Logout Successful",Toast.LENGTH_LONG).show();

             LoginFragment loginFrag = new LoginFragment();
             getActivity().getSupportFragmentManager().beginTransaction()
                     .replace(R.id.loginFrag, loginFrag,"Find this Fragment")
                     .addToBackStack(null)
                     .commit();

             Intent intent = new Intent(getContext(), LoginFragment.class);
             startActivity(intent);

         }
     });
        return view;
    }


}
