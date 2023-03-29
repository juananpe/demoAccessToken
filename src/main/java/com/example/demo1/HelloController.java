package com.example.demo1;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import social.bigbone.MastodonClient;
import social.bigbone.api.Scope;
import social.bigbone.api.entity.Application;
import social.bigbone.api.exception.BigBoneRequestException;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {

        welcomeText.setText("Welcome to JavaFX Application!");

        final MastodonClient client = new MastodonClient.Builder("mastodon.social").build();
        try {
            final Application application = client.apps().createApp(
                    "bigbone-sample-app",
                    "urn:ietf:wg:oauth:2.0:oob",
                    new Scope(Scope.Name.ALL),
                    ""
            ).execute();


            var trustedClient = new MastodonClient.Builder("mastodon.social")
                    .withTrustAllCerts()
                    .build();

            var accesToken = trustedClient.oauth().getAccessTokenWithClientCredentialsGrant(
                    application.getClientId(),
                    application.getClientSecret(),
                    "https://example.org/",
                    new Scope(Scope.Name.ALL)).execute();



            System.out.println("access_token=" + accesToken.getAccessToken());
            System.out.println("client_id=" + application.getClientId());
            System.out.println("client_secret=" + application.getClientSecret());
        } catch (BigBoneRequestException e) {
            e.printStackTrace();
        }



    }
}
