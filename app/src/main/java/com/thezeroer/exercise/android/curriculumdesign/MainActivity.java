package com.thezeroer.exercise.android.curriculumdesign;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.thezeroer.nexalithic.client.NexalithicClient;
import com.thezeroer.nexalithic.client.security.DefaultClientSecurityPolicy;
import com.thezeroer.nexalithic.core.io.thread.LoopThread;
import com.thezeroer.nexalithic.core.messaging.task.NexalithicTask;
import com.thezeroer.nexalithic.core.messaging.task.TaskFuture;
import com.thezeroer.nexalithic.core.model.packet.BusinessPacket;
import com.thezeroer.nexalithic.core.model.packet.payload.TextPayload;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

public class MainActivity extends AppCompatActivity {
    public static final Logger logger = LoggerFactory.getLogger(MainActivity.class);
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        textView = findViewById(R.id.tv);
        try {
            testDependencyLink();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void testDependencyLink() throws Exception {
        LoopThread.GlobalLoopBufferPool_Capacity.set(2);
        LoopThread.LocalLoopBufferPool_Capacity.set(2);
        NexalithicClient nexalithicClient = NexalithicClient.builder()
                .securityPolicy(new DefaultClientSecurityPolicy() {
                    @Override
                    public int getServerCertificatesLength() {
                        return 40;
                    }

                    @Override
                    public void CertificatesFormBuffer(ByteBuffer buffer) {

                    }

                    @Override
                    public boolean verifyOfLeafCertificate(ByteBuffer buffer) {
                        return true;
                    }
                })
                .build();
        nexalithicClient.start();

        new Thread(() -> {
            try {
                nexalithicClient.link(new InetSocketAddress("aly.thezeroer.com", 7709));
                TaskFuture future = nexalithicClient.submit(NexalithicTask.builder()
                        .onRequest(() -> BusinessPacket.create(BusinessPacket.Way.DEFAULT).attach(new TextPayload("Hello Server!")))
                        .onResponse(response -> {
                            if (response.firstPayload() instanceof TextPayload textPayload) {
                                logger.debug(textPayload.value());
                                runOnUiThread(() -> {
                                    textView.append("\n" + textPayload.value());
                                });
                            }
                        })
                        .onTimeout(() -> {
                            logger.debug("Timeout!");
                            runOnUiThread(() -> {
                                textView.append("\nTimeout!");
                            });
                        })
                        .onFinish(() -> {
                            logger.debug("Finish!");
                            runOnUiThread(() -> {
                                textView.append("\nFinish!");
                            });
                        })
                );
                future.waitFinish();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}