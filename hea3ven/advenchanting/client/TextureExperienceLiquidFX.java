package hea3ven.advenchanting.client;

import java.util.Random;

import cpw.mods.fml.client.FMLTextureFX;
import hea3ven.advenchanting.common.CommonProxy;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraftforge.client.ForgeHooksClient;

public class TextureExperienceLiquidFX extends FMLTextureFX {
	protected float red[];
	protected float green[];
	protected float blue[];

	private float particles[];

	private final String texture;
	private int PARTICLES_COUNT = 23;

	private Random rand;

	public TextureExperienceLiquidFX() {
		super(1);

		this.texture = CommonProxy.BLOCK_PNG;

		rand = new Random();

		setup();
	}

	@Override
	public void setup() {
		super.setup();

		red = new float[tileSizeSquare];
		green = new float[tileSizeSquare];
		blue = new float[tileSizeSquare];

		particles = new float[PARTICLES_COUNT * 4];
		for (int i = 0; i < PARTICLES_COUNT; i++) {
			CreateParticle(i);
		}
	}

	private void CreateParticle(int i) {
		particles[i * 4] = rand.nextInt(200);
		particles[i * 4 + 1] = rand.nextFloat() * 5;
		particles[i * 4 + 2] = (float) (rand.nextFloat() * 0.01 * 2 * Math.PI);
		particles[i * 4 + 3] = rand.nextFloat() * 15;
	}

	@Override
	public void bindImage(RenderEngine renderengine) {
		ForgeHooksClient.bindTexture(texture, 0);
	}

	@Override
	public void onTick() {
		try {
		for (int i = 0; i < tileSizeBase; ++i) {
			for (int j = 0; j < tileSizeBase; ++j) {
				this.red[i * tileSizeBase + j] = 0.435F;
				this.green[i * tileSizeBase + j] = 0.71F;
				this.blue[i * tileSizeBase + j] = 0.451F;
			}
		}
		for (int i = 0; i < PARTICLES_COUNT; i++) {
			drawParticle(i);
		}

		// for (int i = 0; i < tileSizeBase; ++i) {
		// for (int j = 0; j < tileSizeBase; ++j) {
		// if(i == j)
		// {
		// this.red[i * tileSizeBase + j] = 0.0F;
		// this.green[i * tileSizeBase + j] = 0.0F;
		// this.blue[i * tileSizeBase + j] = 0.0F;
		// }
		// else
		// {
		// this.red[i * tileSizeBase + j] = 1.0F;
		// this.green[i * tileSizeBase + j] = 1.0F;
		// this.blue[i * tileSizeBase + j] = 1.0F;
		// }
		// float var3 = 0.0F;
		//
		// for (int k = i - 1; k <= i + 1; ++k) {
		// int r = k & tileSizeMask;
		// int g = j & tileSizeMask;
		// var3 += this.red[r + g * tileSizeBase];
		// }
		//
		// this.green[i + j * tileSizeBase] = var3 / 3.3F + this.blue[i + j
		// * tileSizeBase] * 0.8F;
		// }
		// }
		//
		// for (int i = 0; i < tileSizeBase; ++i) {
		// for (int j = 0; j < tileSizeBase; ++j) {
		// this.blue[i + j * tileSizeBase] += this.alpha[i + j *
		// tileSizeBase] * 0.05F;
		//
		// if (this.blue[i + j * tileSizeBase] < 0.0F) {
		// this.blue[i + j * tileSizeBase] = 0.0F;
		// }
		//
		// this.alpha[i + j * tileSizeBase] -= 0.1F;
		//
		// if (Math.random() < 0.05D) {
		// this.alpha[i + j * tileSizeBase] = 0.5F;
		// }
		// }
		// }

		// float af[] = green;
		// green = red;
		// red = af;
		for (int i1 = 0; i1 < tileSizeSquare; i1++) {
			float f1 = red[i1];
			if (f1 > 1.0F) {
				f1 = 1.0F;
			}
			if (f1 < 0.0F) {
				f1 = 0.0F;
			}
			float f2 = green[i1];
			if (f2 > 1.0F) {
				f2 = 1.0F;
			}
			if (f2 < 0.0F) {
				f2 = 0.0F;
			}
			float f3 = blue[i1];
			if (f3 > 1.0F) {
				f3 = 1.0F;
			}
			if (f3 < 0.0F) {
				f3 = 0.0F;
			}
			int r = (int) (f1 * 255);
			int g = (int) (f2 * 255);
			int b = (int) (f3 * 255);
			if (anaglyphEnabled) {
				int i3 = (r * 30 + g * 59 + b * 11) / 100;
				int j3 = (r * 30 + g * 70) / 100;
				int k3 = (r * 30 + b * 70) / 100;
				r = i3;
				g = j3;
				b = k3;
			}

			imageData[i1 * 4 + 0] = (byte) r;
			imageData[i1 * 4 + 1] = (byte) g;
			imageData[i1 * 4 + 2] = (byte) b;
			imageData[i1 * 4 + 3] = (byte) 255;
		}
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	private void drawParticle(int i) {
		float amplitude = particles[i * 4 + 1];
		float freq = particles[i * 4 + 2];

		float w = (float) Math.sin(freq * particles[i * 4]);
		int x = 15 - (Math.round(particles[i * 4] * 0.1f + particles[i * 4 + 3]) % 16);
		int y = Math.round(amplitude * 3 + amplitude * w);
		if (y < 0)
			y = tileSizeBase + x;
		else if (y >= tileSizeBase)
			y = y - tileSizeBase;

		this.red[x * tileSizeBase + y] = Math.abs(w) * 0.396F + 0.349F;
		this.green[x * tileSizeBase + y] = Math.abs(w) * 0.259F + 0.537F;
		this.blue[x * tileSizeBase + y] = Math.abs(w) * 0.051F + 0.11F;

		float w2 = (float) Math.sin(freq * particles[i * 4] + Math.PI / 2);
		float r = Math.abs(w2) * 0.396F + 0.349F;
		float g = Math.abs(w2) * 0.259F + 0.537F;
		float b = Math.abs(w2) * 0.051F + 0.11F;

		if (x > 0) {
			this.red[(x - 1) * tileSizeBase + y] = r;
			this.green[(x - 1) * tileSizeBase + y] = g;
			this.blue[(x - 1) * tileSizeBase + y] = b;
		}

		if (x < (tileSizeBase - 1)) {
			this.red[(x + 1) * tileSizeBase + y] = r;
			this.green[(x + 1) * tileSizeBase + y] = g;
			this.blue[(x + 1) * tileSizeBase + y] = b;
		}

		if (y > 0) {
			this.red[x * tileSizeBase + y - 1] = r;
			this.green[x * tileSizeBase + y - 1] = g;
			this.blue[x * tileSizeBase + y - 1] = b;
		}

		if (y < (tileSizeBase - 1)) {
			this.red[x * tileSizeBase + y + 1] = r;
			this.green[x * tileSizeBase + y + 1] = g;
			this.blue[x * tileSizeBase + y + 1] = b;
		}
		particles[i * 4] += 1;
		if (particles[i * 4] >= 700)
			CreateParticle(i);
	}
}