<?php

namespace App\Http\Controllers\API;

use App\Http\Controllers\Controller;
use Illuminate\Http\Request;
use App\Models\Coment;

class comentController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
      
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        try {
            $Coment = new Coment;
            $Like->user_id = $request->user_id;
            $Like->post_id = $request->post_id;
            $Coment->coment = $request->coment;
            $Coment->save();
            return response()->json([
                "message" => "store berhasil",
                "data" => $Coment
            ]);
        } catch (\Exception $e) {
            return response()->json([
                "message" => "Gagal store data"
            ]);
        }

    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($post_id)
    {
        $Coment = Coment::where("post_id","=",$post_id);
        if ($Coment) {
            return response()->json([
                "data"=>$Coment
            ]);
        } else {
            return response()->json([
                "message" => "Gagal store data"
            ]);
        }
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id)
    {
        try {
            $Coment = Coment::find($id);
            $Coment->coment = $request->coment;
            $Coment->save();
            return response()->json([
                "message" => "update berhasil",
                "data" => $Coment
            ]);
        } catch (\Exception $e) {
            return response()->json([
                "message" => "Gagal update data"
            ]);
        }
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        $Coment = Coment::find($id);
        if($Coment){
            $Coment->delete();
            return response()->json([
                "message" => "Berhasil menghapus data"
            ]);
        } else {
            return response()->json([
                "message" => "Gagal menghapus data"
            ]);
        }

    }
}
