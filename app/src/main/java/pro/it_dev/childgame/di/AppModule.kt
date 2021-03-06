package pro.it_dev.childgame.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pro.it_dev.childgame.presentation.android_bitmap_factory.AssetsFilesBitmapFactory
import pro.it_dev.childgame.presentation.android_bitmap_factory.IBitmapFactory
import pro.it_dev.childgame.presentation.fxplayer.AssetAndroidMediaPlayer
import pro.it_dev.childgame.presentation.fxplayer.IFxPlayer
import pro.it_dev.childgame.repository.AssetFileManager
import pro.it_dev.childgame.repository.IFileManager
import pro.it_dev.childgame.repository.IRepository
import pro.it_dev.childgame.repository.LocalRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

	@Singleton
	@Provides
	fun provideRepository(fm: IFileManager): IRepository{
		return LocalRepository(fm)
	}
	@Singleton
	@Provides
	fun provideFileManager(@ApplicationContext ctx: Context): IFileManager{
		return AssetFileManager(ctx.assets)
	}

	@Singleton
	@Provides
	fun provideFxPlayer(@ApplicationContext ctx: Context): IFxPlayer{
		return AssetAndroidMediaPlayer(ctx)
	}
	@Singleton
	@Provides
	fun provideBitmapFactory(fm: IFileManager): IBitmapFactory{
		return AssetsFilesBitmapFactory(fm)
	}
}