<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::middleware('auth:sanctum')->get('/user', function (Request $request) {
    return $request->user();
});

use App\Http\Controllers\API\userController;
Route::get("user/index", [userController::class, 'index']);
Route::post("user/store", [userController::class, 'store']);
Route::post("user/update/{id}", [userController::class, 'update']);
Route::get("user/destroy/{id}", [userController::class, 'destroy']);
Route::get("user/show/{id}", [userController::class, 'show']);

use App\Http\Controllers\API\postController;
Route::get("post/index", [postController::class, 'index']);
Route::post("post/store", [postController::class, 'store']);
Route::post("post/update/{id}", [postController::class, 'update']);
Route::get("post/destroy/{id}", [postController::class, 'destroy']);
Route::get("post/show/{id}", [postController::class, 'show']);

use App\Http\Controllers\API\likeController;
Route::post("like/store", [likeController::class, 'store']);
Route::get("like/destroy/{id}", [likeController::class, 'destroy']);
Route::get("like/show/{id}", [likeController::class, 'show']);

use App\Http\Controllers\API\comentController;
Route::post("coment/store", [comentController::class, 'store']);
Route::post("coment/update/{id}", [comentController::class, 'update']);
Route::get("coment/destroy/{id}", [comentController::class, 'destroy']);
Route::get("coment/show/{id}", [comentController::class, 'show']);

